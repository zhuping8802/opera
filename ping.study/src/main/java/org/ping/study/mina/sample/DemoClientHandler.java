package org.ping.study.mina.sample;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class DemoClientHandler extends IoHandlerAdapter {

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("Client说：" + message.toString());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("Client,execeptionCaught" + cause.getMessage());
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("Client:inputClosed");
		session.close(true);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("Client:会话关闭");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Client:会话创建");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Client:会话失效");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Client:会话打开");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("Server对Client说：" + message.toString());
	}

}
