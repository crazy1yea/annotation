package com.example.annotation.util.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ExecutorManager implements CommandLineRunner {

	@Value("${executor.run:false}")
	private boolean flag;
	@Value("${executor.minCycleTime:1000}")
	private long minCycleTime;

	@Override
	public void run(String... args) throws Exception {
		if(this.flag){
			log.info("执行器开启状态 : "+flag);
			log.info("执行器最小循环间隙 : "+minCycleTime);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					init();
				}
			}).start();
		}
	}

	private void init() {
		long startTime, lazyTime;
		ExecutorService executorService = null;
		while (true) {
			startTime = System.currentTimeMillis();
			log.debug("hometrend123:" + (executorService == null));
			if (!ObjectUtils.isEmpty(executorService)) {
				log.debug("hometrend456:" + (executorService.isShutdown()));
				log.debug("hometrend789:" + (executorService.isTerminated()));
			}

			if (ObjectUtils.isEmpty(executorService) || executorService.isShutdown()) {
				log.debug("执行器初始化");
				//executorService = Executors.newSingleThreadExecutor();
				executorService = Executors.newFixedThreadPool(3);
				// 定时器详情
				executorTask(executorService);

				lazyTime = minCycleTime - (System.currentTimeMillis() - startTime);
				log.debug("running lazyTime:" + lazyTime);
				if (lazyTime > 500) {
					try {
						log.debug("thread is going to sleep {} ms", lazyTime);
						Thread.sleep(lazyTime);
					} catch (InterruptedException e) {
						log.error("lazy-error:", e);
					}
				}
			} else {
				log.debug("线程正在运行，跳过此次循环");
			}
		}
	}
	/**
	 * 定时执行任务详情
	 * 2019年5月14日- 下午2:57:33
	 * @param executorService
	 */
	private void executorTask(ExecutorService executorService) {
		try {
			long startTime = System.currentTimeMillis();
			
			executorService.submit(new RunableExtension("任务1"));
			
			executorService.shutdown();
			while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
				long usedTime = System.currentTimeMillis() - startTime;
				if (usedTime >= 10 * 1000) {//任务超时强行终止
					executorService.shutdownNow();
				} 
				log.debug("events is still running");
			}
			log.debug("log use time all events end,use time {} ms", (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			// TODO: handle exception
			log.error("executorTask handle exception");
		}
	}
}
