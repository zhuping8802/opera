package org.ping.study.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 线程工具类
 * @author ping.zhu
 *
 */
public final class ThreadUtil {
	
	/**
	 * 线程个数
	 */
	private static final int THREAD_TOTAL = Runtime.getRuntime().availableProcessors() * 2;
	
	/**
	 * 线程池
	 */
	private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_TOTAL);
	
	/**
	 * 获得线程池
	 * @return
	 */
	public  static synchronized ExecutorService getExecutorService(){
		if(executorService == null){
			executorService = Executors.newFixedThreadPool(THREAD_TOTAL);
		}
		return executorService;
	}
	
	/**
	 * 关闭线程池
	 */
	public static void closeThreadPool(){
		executorService.shutdown();
	}
	
	/**
	 * 把线程加入线程池
	 * @param thread
	 */
	public static void putThreadPool(Runnable thread){
		executorService.execute(thread);
	}
	
	/**
	 * 可获取线程返回结果的方式提交线程
	 * @param thread
	 * @return
	 */
	public static Future<?> submitThreadPool(Runnable thread){
		return executorService.submit(thread);
	}
	
	private ThreadUtil() {
		
	}
}
