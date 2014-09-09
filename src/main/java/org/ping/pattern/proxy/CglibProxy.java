package org.ping.pattern.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib动态代理
 * @author ping
 *
 */
public class CglibProxy implements MethodInterceptor {

	private Object target;

	private CglibProxy(Object o) {

		this.target = o;

	}

	@SuppressWarnings("unchecked")
	public static <T> T getProxy(T t) {
		Enhancer en = new Enhancer();
		en.setSuperclass(t.getClass());
		en.setCallback(new CglibProxy(t));
		T tt = (T) en.create();
		return tt;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("拦截前...");
		Object o = method.invoke(target, args);
		System.out.println("拦截后....");
		return o;
	}

}
