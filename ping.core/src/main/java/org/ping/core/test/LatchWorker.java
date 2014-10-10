package org.ping.core.test;

import java.util.concurrent.CountDownLatch;

public abstract class LatchWorker implements Runnable {

	private CountDownLatch startSignal;
	private CountDownLatch doneSignal;
	private Object[] params;
	private int paramsIndex = 0;

	public void init(CountDownLatch startSignal, CountDownLatch doneSignal, Object[] params) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
		this.params = params;
		if (paramsIndex != 0) {
			paramsIndex = 0;
		}
	}

	@Override
	public void run() {
		try {
			Object param = null;
			if (params != null) {
				param = params[paramsIndex];
				paramsIndex++;
			} 
			
			startSignal.await();
			
			long start = System.nanoTime();
			doWork(param);
			long end = System.nanoTime();
			//System.out.println(Thread.currentThread().getName() + " take time(ms):" + (end - start) / 1000000);
			ConcurrentTestUtil.times.add(end - start);
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + "执行失败,错误原因:" + e.getMessage());
		} finally {
			doneSignal.countDown();
		}
	}

	public abstract void doWork(Object param);

}
