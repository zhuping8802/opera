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
import org.ping.spring.json.impl.ObjectFilterPropertyHandler;
import org.springframework.web.bind.annotation.RequestBody;

@Aspect
public class ObjectJsonFilterAspect {
	
	private static final Logger LOG = Logger.getLogger(ObjectJsonFilterAspect.class);
	
	@Pointcut("execution(* org.ping..*.*(..))")
	public void anyMethod(){
		
	}
	
	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object returnVal = pjp.proceed();
		try {
			Method method = ((MethodSignature) pjp.getSignature()).getMethod();
			RequestBody requestBody = method.getAnnotation(RequestBody.class);
			if(requestBody != null){ // 必须有此注解
				FilterPropertyHandler filterPropertyHandler = new ObjectFilterPropertyHandler();
				returnVal = filterPropertyHandler.filterProperties(method, returnVal);
			}
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
