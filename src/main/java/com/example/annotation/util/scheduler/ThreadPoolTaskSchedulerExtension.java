package com.example.annotation.util.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池方法扩展 增加线程名称与执行开始时间标记
 * 2019年5月14日
 */
@Slf4j
public class ThreadPoolTaskSchedulerExtension extends ThreadPoolTaskScheduler {

	private static final long serialVersionUID = 8597664081853146659L;
	
	private final List<String> nameList = new ArrayList<>(); // 当前执行的任务名及开始时间
	private final Map<String, Long> activeMap = new Hashtable<>(); // 当前执行的任务名及开始时间
	/**
	 * schedule 方法重写增加线程名与开始时间记录
	 * 2019年5月14日- 上午11:06:38
	 * @param runnable
	 * @param trigger
	 * @param threadName
	 * @return
	 */
	public ScheduledFuture<?> schedule(final Runnable runnable, Trigger trigger, String name) {
		log.info("timer:" + name);
		nameList.add(name);
		log.info("timer-thread-size++:" + nameList.size());
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				try {
					activeMap.put(name, startTime);
					log.info("timer-{}-start", name);
					runnable.run();
				} catch (Exception e) {
					log.error("timer-" + name + "-error", e);
				} finally {
					activeMap.remove(name);
					log.info("timer-{}-end,duration:{}s", name, (System.currentTimeMillis() - startTime) / 1000f);
				}

			}
		}, name);
		ScheduledFuture<?> future = super.schedule(thread, trigger);

		return future;
	}

	public Map<String, Long> getActiveMap() {
		return new HashMap<String, Long>(activeMap);
	}
	public List<String> getNameList() {
        return new ArrayList<String>(nameList);
    }
}
