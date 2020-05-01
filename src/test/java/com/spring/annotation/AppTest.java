package com.spring.annotation;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.annotation.config.ConfigInitializer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ConfigInitializer.class})
public class AppTest {
	@Autowired
	private DataSource dataSource;
	@Test
	public void getContextConfiguration() {
		System.out.println(dataSource);
	}
}
