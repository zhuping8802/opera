package org.ping.util;

import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

/**
 * 获取spring管理对象的目标对象
 * @author ping.zhu
 *
 */
public final class SpringAopTargetUtils {
	
	private SpringAopTargetUtils(){
		
	}
	
	/**
	 * 获取 目标对象
	 * @param proxy 代理对象
	 * @return 
	 * @throws Exception
	 */
	public static Object getTarget(Object proxy) throws Exception {
		if(!AopUtils.isAopProxy(proxy)) {
			return proxy;//不是代理对象
		}
		if(AopUtils.isJdkDynamicProxy(proxy)) {
			return getJdkDynamicProxyTargetObject(proxy);
		} else { //cglib
			return getCglibProxyTargetObject(proxy);
		}
	}
	
	/**
	 * 获取cglib代理目标对象
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        field.setAccessible(true);
        Object dynamicAdvisedInterceptor = field.get(proxy);
        
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        
        return target;
	}

	/**
	 * 获取jdk代理目标对象
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
		Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        field.setAccessible(true);
        AopProxy aopProxy = (AopProxy) field.get(proxy);
        
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        
        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
        
        return target;
	}
	
}
