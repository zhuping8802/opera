package org.ping.spring.json.annotation;

/**
 * 对象字段过滤器集合
 * @author ping.zhu
 *
 */
public @interface ObjectJsonFilters {

	ObjectJsonFilter[] value();
}
