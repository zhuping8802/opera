package org.ping.dynamic;

import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * javassist 动态操作Java类
 * @author ping
 *
 */
@Controller
@RequestMapping("javassist")
public class JavassistDemo {
	
	private static final Logger LOG = Logger.getLogger(JavassistDemo.class);

	/**
	 * 得到当前类JavassistDemo相关信息
	 */
	@RequestMapping("demo1")
	@ResponseBody
	public Map<String, Object> demo1(){
		Map<String, Object> map = new HashMap<String, Object>();
		ClassPool classPool = ClassPool.getDefault();
		try {
			CtClass ctClass = classPool.getCtClass("javassistDemo");
			if(ctClass != null){
				map.put("包名", ctClass.getPackageName());
				map.put("类名", ctClass.getName() + ";" + ctClass.getSimpleName());
				map.put("类的方法", ctClass.getDeclaredMethods());
			}
		} catch (NotFoundException e) {
			LOG.error(e);
		}
		return map;
	}
}
