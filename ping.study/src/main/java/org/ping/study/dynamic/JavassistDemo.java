package org.ping.study.dynamic;

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
			// 加载失败，原因，是当前类已被spring加载进jvm,main()方式可以正常加载
			CtClass ctClass = classPool.getCtClass("org.ping.dynamic.JavassistDemo");
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
	
	public static void main(String[] args) {
		ClassPool classPool = ClassPool.getDefault();
		try {
			CtClass ctClass = classPool.getCtClass("org.ping.dynamic.JavassistDemo");
			if(ctClass != null){
				System.out.println(ctClass.getPackageName());
				System.out.println(ctClass.getName() + ";" + ctClass.getSimpleName());
				System.out.println(ctClass.getDeclaredMethods());
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
}
