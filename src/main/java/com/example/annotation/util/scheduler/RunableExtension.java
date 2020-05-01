package com.example.annotation.util.scheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义 Runnable
 * 2019年5月14日
 */
@Slf4j
public class RunableExtension implements Runnable {
	private String name;
	
	public RunableExtension(String name){
		this.name = name;
	}
	@Override
	public void run() {
		this.call();
	}
	private boolean call(){
		try {
			//Thread.sleep(10010);
			log.debug(this.name);
		} catch (Exception e) {
			log.error("thread.sleep",e);
		}
		return false;
	}
}
