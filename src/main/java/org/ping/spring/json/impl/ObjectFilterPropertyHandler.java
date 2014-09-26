package org.ping.spring.json.impl;

import java.lang.reflect.Method;

import org.ping.spring.context.WebContext;
import org.ping.spring.json.FilterPropertyHandler;
import org.ping.spring.json.annotation.ObjectJsonFilter;
import org.ping.spring.json.annotation.ObjectJsonFilters;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectFilterPropertyHandler implements FilterPropertyHandler {

	@Override
	public Object filterProperties(Method method, Object object) {
		ObjectJsonFilter objectJsonFilter = method.getAnnotation(ObjectJsonFilter.class);
		ObjectJsonFilters objectJsonFilters = method.getAnnotation(ObjectJsonFilters.class);
		ObjectMapper objectMapper = new ObjectMapper();
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
			throw new RuntimeException(e);
		}
		return null;
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
