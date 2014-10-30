package org.ping.study.concurrent;

import java.util.concurrent.Exchanger;

public class MultiExchangerDemo {

	private volatile String name = "A";
	private volatile int count = 1;

	private Exchanger<String> exchanger = new Exchanger<String>();

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (count <= 10) {
					if(name.equals("A")){
						System.out.print("A");
						try {
							name = "B";
							exchanger.exchange("B");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (count <= 10) {
					if(name.equals("B")){
						System.out.print("B");
						try {
							name = "C";
							exchanger.exchange("C");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (count <= 10) {
					if(name.equals("C")){
						System.out.println("C");
						try {
							name = "A";
							count++;
							exchanger.exchange("A");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
		
	}

	public static void main(String[] args) {
		new MultiExchangerDemo().start();
	}

}
