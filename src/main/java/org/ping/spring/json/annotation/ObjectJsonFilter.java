package org.ping.spring.json.annotation;

/**
 * 对象字段过滤器
 * @author ping.zhu
 *
 */
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
