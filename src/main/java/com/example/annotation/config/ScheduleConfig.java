package com.example.annotation.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
/**
 * 项目中定时器的配置
 * 2020年5月1日
 */
public class ScheduleConfig implements SchedulingConfigurer{

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		//设定定时器最大线程数
		ScheduledExecutorService executors = Executors.newScheduledThreadPool(15);
		taskRegistrar.setScheduler(executors);
		
	}

}
