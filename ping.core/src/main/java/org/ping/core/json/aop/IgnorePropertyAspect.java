package org.ping.core.json.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ping.core.json.service.FilterPropertyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * json字段过滤拦截器
 * 
 * @author ping.zhu
 *
 */
@Component
@Aspect
public class IgnorePropertyAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(IgnorePropertyAspect.class);
	
	private boolean isDynamicProperties = true;

	@Autowired
	@Qualifier("javassistFilterPropertyHandler")
	private FilterPropertyHandler javassistFilterPropertyHandler;

	@Autowired
	@Qualifier("objectFilterPropertyHandler")
	private FilterPropertyHandler objectFilterPropertyHandler;

	@Pointcut("execution(* org.ping..*.*(..))")
	private void anyMethod() {

	}

	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object returnVal = pjp.proceed();
		try {
			Method method = ((MethodSignature) pjp.getSignature()).getMethod();
			ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
			if (responseBody != null) { // 必须有此注解
				if (isDynamicProperties) {
					// 高级动态属性字段过滤
					javassistFilterPropertyHandler.filterProperties(method, returnVal);
				} else{
					// 普通动态属性字段过滤
					objectFilterPropertyHandler.filterProperties(method, returnVal);
				}
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(this.getClass().getName() + ".doAround=============={}", e.getMessage());
		}
		return returnVal;
	}

	@AfterThrowing(pointcut = "anyMethod()", throwing = "e")
	public void doAfterThrowing(Exception e) {
		LOGGER.error(this.getClass().getName() + ".doAfterThrowing=============={}", e.getMessage());
	}
}
