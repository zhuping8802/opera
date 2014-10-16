package org.ping.core.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象字段过滤器
 * @author ping.zhu
 *
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectJsonFilter {
	/**
	 * 目标对象
	 * @return
	 */
	Class<?> target() default Object.class;
	
	/**
	 * 最小字段属性集对象
	 * @return
	 */
	Class<?> mixin() default Object.class;
}
