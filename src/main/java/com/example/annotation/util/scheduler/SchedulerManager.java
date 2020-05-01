package com.example.annotation.util.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.support.CronTrigger;

import com.alibaba.druid.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时器 2019年5月14日
 */

@Slf4j
@Configuration
public class SchedulerManager implements CommandLineRunner {

	@Value("${scheduler.falg:false}")
	private boolean falg;

	@Value("${scheduler.corn:}")
	private String cron;
	
	@Value("${scheduler.listener.interval:60000}")
    private long interval;

	private ThreadPoolTaskSchedulerExtension theadPool;

	@Override
	public void run(String... args) throws Exception {
		init();
	}

	private void init() {
		if(falg){//判断定时器是否开启
			theadPool = new ThreadPoolTaskSchedulerExtension();
			theadPool.initialize();
			theadPool.setPoolSize(2);
			timerTask("1");
			timerTask("2");
			timerTask("3");
			timerTask("4");
			timerTask("5");
			timerTask("6");
			timerTask("7");
			timerTask("8");
			/*
			ThreadListener listener = new ThreadListener(theadPool,interval);
			listener.start();
			*/
		}
	}

	private void timerTask(String threadTaskName){
		if(!StringUtils.isEmpty(this.cron)){
			log.info("corn <<>> " + this.cron);
			CronTrigger cronTrigger = new CronTrigger(cron);
			theadPool.schedule(new Runnable() {
				@Override
				public void run() {
					log.info("测试线程定时任务！ "+threadTaskName);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						log.error("Thread.sleep Exception ",e);
					}
				}
			}, cronTrigger, threadTaskName);
		}
	}
}
