package com.example.annotation.util.scheduler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 扩展线程类 2019年5月14日
 */
@Slf4j
public class ThreadListener extends Thread {
	private ThreadPoolTaskSchedulerExtension threadPool;// 线程池
	private long interval;// 时间间隔

	public ThreadListener(ThreadPoolTaskSchedulerExtension threadPool, long interval) {
		this.threadPool = threadPool;
		this.interval = interval;
		this.setName("TimerListener");
	}

	public void run() {
		if (ObjectUtils.isEmpty(threadPool)) {
			log.info("threadPool is null");
			return;
		}
		while (true) {
			try {
				log.info("timer listener start~");// 间隔
				Thread.sleep(this.interval);

				ScheduledThreadPoolExecutor pool = threadPool.getScheduledThreadPoolExecutor(); // 任务线程池
				BlockingQueue<Runnable> queue = pool.getQueue(); // 队列中的任务
				Map<String, Long> activeMap = threadPool.getActiveMap(); // 运行中的任务名
				List<String> nameList = threadPool.getNameList(); // 线程名称集合

				int totalCount = nameList.size();// 总任务数
				int activeCount = threadPool.getActiveCount();// 当前在执行的任务数量
				long completedCount = pool.getCompletedTaskCount();// 任务执行完成的次数
				int queueSize = queue.size(); // 队列中的数量

				log.info("timer-totalCount:" + totalCount);
				log.info("timer-activeCount:" + activeCount);
				log.info("timer-completedCount:" + completedCount);
				log.info("timer-queueSize:" + queueSize);
				
				if(!ObjectUtils.isEmpty(activeMap)){
					for (Entry<String,Long> entry : activeMap.entrySet()) {
						log.info("timer-{}-runing,duration:{}s", entry.getKey(), (System.currentTimeMillis() - entry.getValue()) / 1000f);
						nameList.remove(entry.getKey());
					}
				};
				
				if (!ObjectUtils.isEmpty(queue)) {
					for (Runnable runnable : queue) {
						Field field;
						try {
							// o初始为内部类无法直接获取
							// java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask
							field = runnable.getClass().getSuperclass().getDeclaredField("callable");
							field.setAccessible(true);// 设置私有属性可读
							Object obj = field.get(runnable); // 读取 callable

							// 此时obj为java.util.concurrent.Executors$RunnableAdapter
							field = obj.getClass().getDeclaredField("task");
							field.setAccessible(true);// 设置私有属性可读
							obj = field.get(obj); // 读取 task 封装过的runnable

							// 此时obj为class
							// org.springframework.scheduling.concurrent.ReschedulingRunnable
							field = obj.getClass().getSuperclass().getDeclaredField("delegate");
							field.setAccessible(true); // 设置私有属性可读
							obj = field.get(obj); // 读取 delegate 即初始传递的runnable

							if (obj instanceof Thread) {
								Thread r = (Thread) obj;
								log.info("queue-task:{}", r.getName());
								nameList.remove(r.getName());
							} else {
								log.info("queue-noname-task:{}", obj);
							}
						} catch (Exception e) {
							log.error("timer listener exception 线程解析异常！", e);
						}
					}
				}
				if (totalCount > queueSize + activeCount) {
					log.error("timer线程缺失:");
					if (!ObjectUtils.isEmpty(nameList)) {
						for (String name : nameList) {
							log.error(name);
						}
					}
				}
			} catch (InterruptedException e) {
				log.error("timer listener exception 中断！", e);
			}
		}

	}
}
