package org.ping.study.spring.json.impl;

import java.lang.reflect.Method;

import org.ping.study.spring.context.WebContext;
import org.ping.study.spring.json.annotation.ObjectJsonFilter;
import org.ping.study.spring.json.annotation.ObjectJsonFilters;
import org.ping.study.spring.json.service.FilterPropertyHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 简单对象字段属性过滤处理
 * @author ping
 *
 */
@Component("objectFilterPropertyHandler")
public class ObjectFilterPropertyHandler implements FilterPropertyHandler{

	@Override
	public void filterProperties(Method method, Object object){
		// 类级别过滤
		Class<?> targetClass = method.getDeclaringClass();
		ObjectJsonFilter classObjectJsonFilter = targetClass.getAnnotation(ObjectJsonFilter.class);
		ObjectJsonFilters classObjectJsonFilters = targetClass.getAnnotation(ObjectJsonFilters.class);
		
		// 方法级别过滤
		ObjectJsonFilter objectJsonFilter = method.getAnnotation(ObjectJsonFilter.class);
		ObjectJsonFilters objectJsonFilters = method.getAnnotation(ObjectJsonFilters.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		if(classObjectJsonFilter != null){
			this.configObjectMapper(classObjectJsonFilter, method, objectMapper);
		}
		
		if(classObjectJsonFilters != null){
			ObjectJsonFilter[] filters = classObjectJsonFilters.value();
			for(ObjectJsonFilter filter : filters){
				this.configObjectMapper(filter, method, objectMapper);
			}
		}
		
		if(objectJsonFilter != null){
			this.configObjectMapper(objectJsonFilter, method, objectMapper);
		}
		
		if(objectJsonFilters != null){
			ObjectJsonFilter[] filters = objectJsonFilters.value();
			for(ObjectJsonFilter filter : filters){
				this.configObjectMapper(filter, method, objectMapper);
			}
		}
		try {
			objectMapper.writeValue(WebContext.getInstance().getResponse().getOutputStream(), object);
		} catch (Exception e) {
			throw new RuntimeException("Could not write JSON: " + e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param objectJsonFilter
	 * @param method
	 * @param config
	 */
	private void configObjectMapper(ObjectJsonFilter objectJsonFilter, Method method, ObjectMapper objectMapper){
		Class<?> target = objectJsonFilter.target();
		Class<?> mixin = objectJsonFilter.mixin();
		if (target != null) { 
			objectMapper.addMixInAnnotations(target, mixin);  
        } else {  
        	objectMapper.addMixInAnnotations(method.getReturnType(), mixin);  
        }
	}

}
