package org.ping.study.mina.sample;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class DemoServerHandler extends IoHandlerAdapter {
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("exceptionCaught:" + cause.getLocalizedMessage());
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("Server:inputClosed");
		session.close(true);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("Server:sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Server:sessionCreated");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Server:sessionIdle");
		session.close(true);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Server:sessionOpened");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("ClientToServer：" + message.toString());
		session.write("Server:" + new Date().toLocaleString());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("ServerToClient:" + message.toString());
	}

}
