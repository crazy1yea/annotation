package com.example.annotation.config;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.stereotype.Controller;

@ComponentScan(value = "com.example.annotation", excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
public class RootConfig {

	@Value("${ftp.host}")
	private String ftpHost;

	@Value("${ftp.port}")
	private int ftpPort;

	@Value("${ftp.user}")
	private String ftpUser;

	@Value("${ftp.password}")
	private String ftpPassword;

	@Value("${ftp.remote.directory}")
	private String ftpRemoteDirectory;

	@Value("${ftp.remote.FileSeparator}")
	private String ftpRemoteFileSeparator;

	@Bean
	public DefaultFtpSessionFactory ftpSessionFactory() {
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
		ftpSessionFactory.setHost(ftpHost);
		ftpSessionFactory.setPort(ftpPort);
		ftpSessionFactory.setUsername(ftpUser);
		ftpSessionFactory.setPassword(ftpPassword);
		ftpSessionFactory.setControlEncoding("UTF-8");
		ftpSessionFactory.setFileType(FTP.BINARY_FILE_TYPE);
		ftpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
		return ftpSessionFactory;
	}


	@Bean("ftpSessionFactory")
	public SessionFactory<FTPFile> ftpSessionFactory(DefaultFtpSessionFactory ftpSessionFactory) {
		CachingSessionFactory<FTPFile> cachingSessionFactory = new CachingSessionFactory<>(ftpSessionFactory);
		cachingSessionFactory.setPoolSize(16);
		return cachingSessionFactory;
	}

	@Bean
	public FtpRemoteFileTemplate ftpRemoteFileTemplate(@Qualifier("ftpSessionFactory") SessionFactory<FTPFile> ftpSessionFactory) {
		FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(ftpSessionFactory);
		template.setCharset("UTF-8");
		template.setAutoCreateDirectory(true);
		template.setRemoteDirectoryExpression(new LiteralExpression(ftpRemoteDirectory));
		template.setUseTemporaryFileName(true);
		template.setRemoteFileSeparator(ftpRemoteFileSeparator);
		template.setExistsMode(FtpRemoteFileTemplate.ExistsMode.NLST);
		return template;
	}
}
