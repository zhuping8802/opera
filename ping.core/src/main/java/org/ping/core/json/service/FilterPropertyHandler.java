package org.ping.core.json.service;

import java.lang.reflect.Method;

/**
 * 过滤属性处理器
 * @author ping.zhu
 * 参考https://github.com/blademainer/YIXUN_1.5_EE.git实现
 *
 */
public interface FilterPropertyHandler {
	
	/**
	 * 通过传入调用方法和返回值过滤属性
	 * @param method
	 * @param object
	 * @return
	 */
	public void filterProperties(Method method, Object object);
}
