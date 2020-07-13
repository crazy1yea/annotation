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
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yangy
 */
@ComponentScan(value = "com.example.annotation", excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
@PropertySource(value = { "classpath:application.properties" })
public class RootConfig {

	@Value("${ftp.host}")
	private String ftpHost;
	@Value("${ftp.port}")
	private String ftpPort;
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
		ftpSessionFactory.setPort(Integer.parseInt(ftpPort));
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

	@Bean
	public ThreadPoolTaskExecutor springExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 此方法返回可用处理器的虚拟机的最大数量; 不小于1
		// 设置核心线程数
		executor.setCorePoolSize(5);
		// 设置最大线程数
		executor.setMaxPoolSize(11);
		// 除核心线程外的线程存活时间
		executor.setKeepAliveSeconds(3);
		// 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
		executor.setQueueCapacity(40);
		// 线程名称前缀
		executor.setThreadNamePrefix("thread-execute");
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}
}
