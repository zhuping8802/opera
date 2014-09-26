package org.ping.spring.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解json过滤pojo内的属性，其他的属性都会被序列化成字符串
 * @author ping.zhu
 *
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreProperty {
	
	/**
	 * 要忽略字段的POJO
	 * @return
	 */
	Class<?> pojo();

	/**
	 * 要忽略的字段名
	 * @return
	 */
	String[] name();
}
