package org.ping.study.mina.sample;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class DemoServer {

	public static void main(String[] args) {
		try {
			// 创建服务端
			IoAcceptor acceptor = new NioSocketAcceptor();
			
			// 设置过滤器
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
			chain.addLast("logger", new LoggingFilter());
			chain.addLast(
					"codec",
					new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); 
			
			IoSessionConfig ioSessionConfig = acceptor.getSessionConfig();
			// 设置读取缓存区大小
			ioSessionConfig.setReadBufferSize(2048);
			// 设置10秒无操作进入空闲状态
			ioSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 60);
			// 设置handler
			acceptor.setHandler(new DemoServerHandler());
			// 绑定端口
			acceptor.bind(new InetSocketAddress(8080));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
