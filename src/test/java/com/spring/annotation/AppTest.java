package com.spring.annotation;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.example.annotation.config.ConfigInitializer;
import com.example.annotation.config.RootConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ContextConfiguration(classes = { ConfigInitializer.class })
public class AppTest {
	@Test
	public void getContextConfiguration() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		log.info("Annotation test！");
		
		/*
		// FTP 相关测试
		FtpRemoteFileTemplate ftpRemoteFileTemplate = context.getBean(FtpRemoteFileTemplate.class);
		log.info("ftpRemoteFileTemplate >>> "+ftpRemoteFileTemplate.list("").length);
		ftpRemoteFileTemplate.execute((session) -> {
			FTPFile[] ftpFiles = session.list("");
			log.info("ftpFiles >>>> "+ftpFiles.length);
			return ftpFiles;
		});

		ftpRemoteFileTemplate.get("out_20200604_1591260545261.zip",inputStream -> {
			try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\lenovo\\Desktop/out_20200604_1591260545261.zip")) {
				IOUtils.copy(inputStream, fileOutputStream);
			}
		});
		*/
	}

}
