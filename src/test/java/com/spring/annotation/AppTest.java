package com.spring.annotation;

import org.springframework.test.context.ContextConfiguration;

import com.example.annotation.config.ConfigInitializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ContextConfiguration(classes = { ConfigInitializer.class })
public class AppTest {

	public void getContextConfiguration() {
		log.info("test");
	}

}
