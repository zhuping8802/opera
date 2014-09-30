package org.ping.study.concurrent;

public class ThreadDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 当前活动线程数
		System.out.println(Thread.currentThread().activeCount());
		// 可用处理器数目
		System.out.println(Runtime.getRuntime().availableProcessors());
	}

}
