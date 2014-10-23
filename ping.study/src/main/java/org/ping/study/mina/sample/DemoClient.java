package org.ping.study.mina.sample;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class DemoClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 建立连接
		IoConnector connector = new NioSocketConnector();
		// 设置过滤器
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		chain.addLast("logger", new LoggingFilter());
		connector.setConnectTimeoutMillis(100);
		connector.setHandler(new DemoClientHandler());

		IoSession session = null;
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					"localhost", 8080));
			future.awaitUninterruptibly();
			session = future.getSession();
			String message = null;
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入内容：");
			while(session.isConnected()){
				message = scanner.next();
				if(!"exit".equalsIgnoreCase(message)){
					session.write(message);
				}else{
					break;
				}
			}
			scanner.close();
			System.out.println("终止内容输入");
//			session.close();
			session.close(true);
		} catch (Exception e) {
			System.out.println("Client:" + e.getMessage());
		}

		// wait until the summation is done
		if(session != null){
			System.out.println("================");
			session.getCloseFuture().awaitUninterruptibly();
		}
		// 释放资源
		connector.dispose();
		System.out.println("Client:失去连接");
	}

}
