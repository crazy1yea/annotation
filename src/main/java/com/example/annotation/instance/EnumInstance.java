package com.example.annotation.instance;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangy
 */

public enum EnumInstance {
	/**
	 * 实例
	 */
	INSTANCE;

	private ThreadPoolExecutor executor;

	private EnumInstance() {
		executor = new ThreadPoolExecutor(
			10,
			15,
			10,
			TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(32),
			Executors.defaultThreadFactory());
	}

	public ThreadPoolExecutor getInstance() {
		return executor;
	}
}