package org.ping.study.concurrent;

import java.util.concurrent.Exchanger;

/**
 * 线程切换实现,Exchanger实现
 * 
 * @author ping.zhu
 *
 */
public class ExchangerDemo {
	private volatile boolean flag = true;
	private volatile int count = 0;
	private volatile int number = 0;

	public ExchangerDemo(boolean flag, int number) {
		super();
		this.flag = flag;
		this.number = number;
	}

	private final Exchanger<Boolean> exchanger = new Exchanger<Boolean>();

	class Thread1 implements Runnable {

		private int printCount = 0;

		public Thread1(int printCount) {
			super();
			this.printCount = printCount;
		}

		@Override
		public void run() {
			while (count < number) {
				if (flag) {
					for (int i = 0; i < printCount; i++) {
						printNumber("thread1");
					}
					try {
						flag = false;
						boolean b  = !exchanger.exchange(flag);
//						System.out.println("thread1" + b);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	class Thread2 implements Runnable {
		private int printCount = 0;

		public Thread2(int printCount) {
			super();
			this.printCount = printCount;
		}

		@Override
		public void run() {
			while (count < number) {
				if (!flag) {
					for (int i = 0; i < printCount; i++) {
						printNumber("thread2");
					}
					try {
						flag = true;
						boolean b  = !exchanger.exchange(flag);
//						System.out.println("thread2" + b);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void printNumber(String name) {
		if (++count <= number) {
			System.out.println(name + "===========" + count);
		}
	}

	public void start() {
		new Thread(new Thread1(3)).start();
		new Thread(new Thread2(3)).start();
	}

	public static void main(String[] args) {
		new ExchangerDemo(true, 59).start();
	}
}