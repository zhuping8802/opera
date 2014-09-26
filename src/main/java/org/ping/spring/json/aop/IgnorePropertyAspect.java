package org.ping.spring.json.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ping.spring.json.FilterPropertyHandler;
import org.ping.spring.json.impl.JavassistFilterPropertyHandler;

/**
 * json字段过滤拦截器
 * @author ping.zhu
 *
 */
@Aspect
public class IgnorePropertyAspect {
	private static final Logger LOG = Logger.getLogger(IgnorePropertyAspect.class);

	@Pointcut("execution(* org.ping..*.*(..))")
	private void anyMethod() {
		
	}

	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object returnVal = pjp.proceed();
		try {
			FilterPropertyHandler filterPropertyHandler = new JavassistFilterPropertyHandler(true);
			Method method = ((MethodSignature) pjp.getSignature()).getMethod();
			returnVal = filterPropertyHandler.filterProperties(method, returnVal);
		} catch (Exception e) {
			LOG.error(this.getClass().getName() + ".doAround==============" + e.getMessage());
		}
		return returnVal;
	}

	@AfterThrowing(pointcut = "anyMethod()", throwing = "e")
	public void doAfterThrowing(Exception e) {
		LOG.error(this.getClass().getName() + ".doAfterThrowing==============" + e.getMessage());
	}
}
