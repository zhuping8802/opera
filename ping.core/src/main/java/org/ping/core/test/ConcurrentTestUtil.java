package org.ping.core.test;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 并发测试工具类
 * @author ping.zhu
 *
 */
public class ConcurrentTestUtil {
	
	static ConcurrentLinkedQueue<Long> times = new ConcurrentLinkedQueue<Long>();

	/**
	 * @param worker
	 *            线程执行者
	 * @param threads
	 *            并发数
	 * @param params
	 *            线程执行需要的参数(模拟不同线程根据不同的参数值执行并发操作)
	 */
	public static void test(LatchWorker worker, int threads, Object[] params) {
		if (threads <= 0) {
			threads = 1;
		}
		
		if (params !=null && params.length != threads) {
			throw new IllegalArgumentException("并发线程数与参数个数不一致.");
		}

		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(threads);
		worker.init(startSignal, doneSignal, params);
		
		for (int i = 1; i <= threads; i++) {
			new Thread(worker, "LatchWorker-" + i).start();
		}

		startSignal.countDown();
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			throw new RuntimeException("doneSignal send failure");
		}
		
		printTotalResult(threads);
	}
	
	/**
	 * @param worker
	 *            线程执行者
	 * @param threads
	 *            并发数
	 * @param times
	 *            重复执行次数
	 * @param params
	 *            线程执行需要的参数(模拟不同线程根据不同的参数值执行并发操作)
	 */
	public static void test(LatchWorker worker, int threads, int times, Object[] params) {
		if (threads <= 0) {
			threads = 1;
		}
		if (times <= 0) {
			times = 1;
		}
		
		if (params !=null && params.length != threads) {
			throw new IllegalArgumentException("并发线程数与参数个数不一致.");
		}
		
		for (int i = 0; i < times; i++) {
			CountDownLatch startSignal = new CountDownLatch(1);
			CountDownLatch doneSignal = new CountDownLatch(threads);
			worker.init(startSignal, doneSignal, params);
			for (int thread = 1; thread <= threads; thread++) {
				new Thread(worker, "LatchWorker" + thread).start();
			}

			startSignal.countDown();
			try {
				doneSignal.await();
			} catch (InterruptedException e) {
				throw new RuntimeException("doneSignal send failure");
			}
		}
		
		printTotalResult(threads);
	}
	
	/**
	 * 打印结果
	 * @param threads
	 */
	private static void printTotalResult(int threads) {
		int timesSize = times.size();
		List<Long> timesList = new LinkedList<Long>();
		for (Iterator<Long> iterator = times.iterator(); iterator.hasNext();) {
			timesList.add(iterator.next()) ;
		}
		Collections.sort(timesList);
		
		System.out.println("统计结果:");
		if (timesList.isEmpty()) {
			System.out.println("所有线程执行失败。");
			return;
		}
		System.out.println("并发线程数:" + threads);
		System.out.println("总执行完线程数:" + timesSize);
		System.out.println("最小线程执行时间(ms):" + timesList.get(0) / 1000000);
		System.out.println("最大线程执行时间(ms):" + timesList.get(timesSize - 1) / 1000000);
		printFirstPercentAvgTime(timesList, timesSize, 50);
		printFirstPercentAvgTime(timesList, timesSize, 60);
		printFirstPercentAvgTime(timesList, timesSize, 70);
		printFirstPercentAvgTime(timesList, timesSize, 80);
		printFirstPercentAvgTime(timesList, timesSize, 90);
		printFirstPercentAvgTime(timesList, timesSize, 100);
	}
	
	private static void printFirstPercentAvgTime(List<Long> timesList, int threads, int percent){
		int size = (threads * percent) / 100;
		if (size == 0) {
			size = 1;
		}
		Long total = 0L;
		List<Long> subList = timesList.subList(0, size);
		for (Long time : subList) {
			total = total + time;
		}
		System.out.println("前"+ percent +"%线程平均执行时间(ms):" + total / size / 1000000);
	}

}
