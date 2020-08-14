package com.example.annotation;

import com.example.annotation.instance.EnumInstance;
import com.example.annotation.model.RunableTask;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 主类
 * 2020年6月9日
 */
@Slf4j
public class AnnotationApplication {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		ThreadPoolExecutor executor = EnumInstance.INSTANCE.getInstance();
		/*
		Map<CallableTask, Future> map = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			CallableTask call = new CallableTask("i -- "+i);
			Future future = executor.submit(call);
			map.put(call, future);
		}
		try {
			Thread.sleep(10000);
			for (Map.Entry<CallableTask, Future> entry: map.entrySet() ) {
				Future future = null;
				try {
					future = entry.getValue();
					future.get(1, TimeUnit.SECONDS);
				} catch (Exception e) {
					future.cancel(true);
				}
			}
			log.info("定时执行结束！");
		} catch (InterruptedException ex) {
			ex.getStackTrace();
		}
		*/
		Map<RunableTask, Future> map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			RunableTask run = new RunableTask("i -- " + i);
			Future future = executor.submit(run);
			map.put(run, future);
		}
		//executor.shutdown();
		try {
			Thread.sleep(10000);
			for (Map.Entry<RunableTask, Future> entry: map.entrySet() ) {
				Future future = null;
				try {
					future = entry.getValue();
					future.get(1, TimeUnit.SECONDS);
				} catch (Exception e) {
					future.cancel(true);
				}
			}
			log.info("定时执行结束！");
		} catch (InterruptedException ex) {
			ex.getStackTrace();
		}
	}
}
