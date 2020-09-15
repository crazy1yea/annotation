package com.example.annotation.config.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.filters.FtpSimplePatternFileListFilter;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizer;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.util.List;

@Slf4j
//@Component
//@EnableIntegration
@PropertySource(value = { "classpath:application.properties" })
public class FtpConfig {

	/** out */
	@Value("${integration.ftp.host}")
	private String integrationFtpHost;
	@Value("${integration.ftp.port}")
	private int integrationFtpPort;
	@Value("${integration.ftp.username}")
	private String integrationFtpUser;
	@Value("${integration.ftp.password}")
	private String integrationFtpPassword;
	@Value("${integration.ftp.remote.directory}")
	private String integrationFtpRemoteDirectory;
	@Value("${integration.ftp.local.directory}")
	private String integrationFtpLocalDirectory;

	@Bean(name = "integrationFtpSessionFactory")
	public DefaultFtpSessionFactory integrationFtpSessionFactory() {
		DefaultFtpSessionFactory ftpSessionFactory = new DefaultFtpSessionFactory(){
			/**
			 * 高并发下和ftp服务器握手会返回response 421 received.错误。因此采用同步。
			 * @return
			 */
			@Override
			public synchronized FtpSession getSession() {
				return super.getSession();
			}
		};
		ftpSessionFactory.setHost(integrationFtpHost);
		ftpSessionFactory.setPort(integrationFtpPort);
		ftpSessionFactory.setUsername(integrationFtpUser);
		ftpSessionFactory.setPassword(integrationFtpPassword);
		ftpSessionFactory.setControlEncoding("UTF-8");
		ftpSessionFactory.setFileType(FTP.BINARY_FILE_TYPE);
		ftpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
		log.info(" ftp 相关配置初始化完成。。。");
		return ftpSessionFactory;
	}


	@Bean("integrationFtpSessionFactory")
	public SessionFactory<FTPFile> integrationFtpSessionFactory(DefaultFtpSessionFactory integrationFtpSessionFactory) {
		CachingSessionFactory<FTPFile> cachingSessionFactory = new CachingSessionFactory<>(integrationFtpSessionFactory);
		cachingSessionFactory.setPoolSize(16);
		cachingSessionFactory.setSessionWaitTimeout(10000);
		return cachingSessionFactory;
	}

	@Bean
	public FtpRemoteFileTemplate integrationFtpRemoteFileTemplate(@Qualifier("integrationFtpSessionFactory") SessionFactory<FTPFile> integrationFtpSessionFactory) {
		FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(integrationFtpSessionFactory);
		template.setCharset("UTF-8");
		template.setAutoCreateDirectory(true);
		template.setRemoteDirectoryExpression(new LiteralExpression(integrationFtpRemoteDirectory));
		template.setUseTemporaryFileName(true);
		template.setExistsMode(FtpRemoteFileTemplate.ExistsMode.NLST);
		return template;
	}

	@Bean
	public FtpInboundFileSynchronizer integrationSftpInboundFileSynchronizer(@Qualifier("integrationFtpSessionFactory") SessionFactory<FTPFile> integrationFtpSessionFactory) {
		FtpInboundFileSynchronizer fileSynchronizer = new FtpInboundFileSynchronizer(integrationFtpSessionFactory);
		fileSynchronizer.setDeleteRemoteFiles(true);
		fileSynchronizer.setRemoteDirectory(integrationFtpRemoteDirectory);
		fileSynchronizer.setFilter(new FtpSimplePatternFileListFilter("*.zip"));
		return fileSynchronizer;
	}

	@Bean
	@ServiceActivator(inputChannel = "lsChannel")
	public MessageHandler lsHandler(@Qualifier("integrationFtpSessionFactory") SessionFactory<FTPFile> integrationFtpSessionFactory) {
		FtpOutboundGateway ftpOutboundGateway = new FtpOutboundGateway(integrationFtpSessionFactory, "ls", "payload");
		ftpOutboundGateway.setOptions("-dirs"); //配置项
		return ftpOutboundGateway;
	}

	@Bean
	@ServiceActivator(inputChannel = "downloadChannel")
	public MessageHandler downloadHandler(@Qualifier("integrationFtpSessionFactory") SessionFactory<FTPFile> integrationFtpSessionFactory) {
		FtpOutboundGateway sftpOutboundGateway = new FtpOutboundGateway(integrationFtpSessionFactory, "mget", "payload");
		sftpOutboundGateway.setOptions("-R");
		sftpOutboundGateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
		sftpOutboundGateway.setLocalDirectory(new File(integrationFtpLocalDirectory));
		sftpOutboundGateway.setAutoCreateLocalDirectory(true);
		return sftpOutboundGateway;
	}

	@Bean
	@ServiceActivator(inputChannel = "uploadChannel")
	public MessageHandler outUploadHandler(@Qualifier("integrationFtpSessionFactory") SessionFactory<FTPFile> integrationFtpSessionFactory) {
		FtpMessageHandler handler = new FtpMessageHandler(integrationFtpSessionFactory);
		handler.setRemoteDirectoryExpression(new LiteralExpression(integrationFtpRemoteDirectory));
		handler.setFileNameGenerator(message -> {
			if (message.getPayload() instanceof File) {
				return ((File) message.getPayload()).getName();
			} else {
				throw new IllegalArgumentException("File expected as payload.");
			}
		});
		return handler;
	}

	@MessagingGateway
	public interface OutFtpGateway {

		@SuppressWarnings("rawtypes")
		@Gateway(requestChannel = "lsChannel")
		List<FileInfo> listFile(String dir);

		@Gateway(requestChannel = "downloadChannel")
		List<File> downloadFiles(String dir);

		@Gateway(requestChannel = "uploadChannel")
		void uploadFile(File file);
	}

}