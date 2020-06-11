package com.example.annotation.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 项目中定时器的配置 2020年5月1日
 */
public class ScheduleConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		//设定定时器最大线程数
		 ThreadPoolExecutor executors = new ThreadPoolExecutor(
				10,//核心线程数
				15,//核心线程 + 临时线程的最大上限
				10,//线程的最大空闲时间
				TimeUnit.SECONDS,//指定超时的时间单位，秒、分、时等
				new ArrayBlockingQueue<Runnable>(32),//等待执行的任务队列
				Executors.defaultThreadFactory());//实现生成线程的方式、定义线程名格式、是否后台执行
		taskRegistrar.setScheduler(executors);
		
	}

}
