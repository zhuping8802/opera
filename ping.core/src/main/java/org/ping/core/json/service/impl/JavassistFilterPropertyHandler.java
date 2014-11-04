package org.ping.core.json.service.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import javax.servlet.http.HttpServletResponse;

import org.ping.core.json.annotation.AllowProperty;
import org.ping.core.json.annotation.IgnoreProperties;
import org.ping.core.json.annotation.IgnoreProperty;
import org.ping.core.json.service.FilterPropertyHandler;
import org.ping.core.util.ReflectUtil;
import org.ping.core.util.StringHelper;
import org.ping.core.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component("javassistFilterPropertyHandler")
public class JavassistFilterPropertyHandler implements FilterPropertyHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(JavassistFilterPropertyHandler.class);

	/**
	 * 注解的方法对应生成的代理类映射表
	 */
	private static Map<Method, Map<Class<?>, Class<?>>> proxydMethodMap = new HashMap<Method, Map<Class<?>, Class<?>>>();

	/**
	 * String数组的hashCode与生成的对应的代理类的映射表
	 */
	private static Map<Integer, Class<?>> proxyMixInAnnotationMap = new HashMap<Integer, Class<?>>();

	private static String[] globalIgnoreProperties = new String[] { "hibernateLazyInitializer", "handler" };

	/**
	 * 创建代理接口的唯一值索引
	 */
	private static int proxyIndex;

	public JavassistFilterPropertyHandler() {
		
	}

	public JavassistFilterPropertyHandler(String[] globalIgnoreProperties) {
		JavassistFilterPropertyHandler.globalIgnoreProperties = globalIgnoreProperties;
	}

	private Collection<String> checkAndPutToCollection(Collection<String> collection, String[] names) {
		if (collection == null) {
			collection = new HashSet<String>();
		}
		Collections.addAll(collection, names);
		return collection;
	}

	private Collection<String> putGlobalIgnoreProperties(Collection<String> collection) {
		if (globalIgnoreProperties != null) {
			if (collection == null) {
				collection = new HashSet<String>();
			}
			for (int i = 0; i < globalIgnoreProperties.length; i++) {
				String name = globalIgnoreProperties[i];
				collection.add(name);
			}
		}
		return collection;
	}

	/**
	 * 处理IgnoreProperties注解
	 * @param properties
	 * @param pojoAndNamesMap
	 */
	private void processIgnorePropertiesAnnotation(IgnoreProperties properties,
			Map<Class<?>, Collection<String>> pojoAndNamesMap) {
		IgnoreProperty[] values = properties.value();
		AllowProperty[] allowProperties = properties.allow();
		if (allowProperties != null) {
			for (AllowProperty allowProperty : allowProperties) {
				processAllowPropertyAnnotation(allowProperty, pojoAndNamesMap);
			}
		}
		if (values != null) {
			for (IgnoreProperty property : values) {
				processIgnorePropertyAnnotation(property, pojoAndNamesMap);
			}
		}
	}

	/**
	 * 处理IgnoreProperty注解
	 * @param property
	 * @param pojoAndNamesMap
	 */
	private void processIgnorePropertyAnnotation(IgnoreProperty property,
			Map<Class<?>, Collection<String>> pojoAndNamesMap) {
		String[] names = property.name();
		Class<?> pojoClass = property.pojo();
		Collection<String> nameCollection = pojoAndNamesMap.get(pojoClass);
		nameCollection = checkAndPutToCollection(nameCollection, names);
		pojoAndNamesMap.put(pojoClass, nameCollection);
	}

	/**
	 * 处理AllowProperty注解
	 * @param property
	 * @param pojoAndNamesMap
	 */
	private void processAllowPropertyAnnotation(AllowProperty property,
			Map<Class<?>, Collection<String>> pojoAndNamesMap) {
		String[] allowNames = property.name();
		Class<?> pojoClass = property.pojo();

		Collection<String> ignoreProperties = ReflectUtil
				.getUnstaticClassFieldNameCollection(pojoClass);

		Collection<String> allowNameCollection = new ArrayList<String>();
		Collections.addAll(allowNameCollection, allowNames);

		Collection<String> nameCollection = pojoAndNamesMap.get(pojoClass);
		if (nameCollection != null) {
			nameCollection.removeAll(allowNameCollection);
		} else {
			ignoreProperties.removeAll(allowNameCollection);
			nameCollection = ignoreProperties;
		}
		pojoAndNamesMap.put(pojoClass, nameCollection);
	}

	/**
	 * 根据方法获取过滤映射表
	 * @param method
	 *            注解了 @IgnoreProperties 或 @IgnoreProperty 的方法（所在的类）
	 * @return Map<Class<?>, Collection<Class<?>>> pojo与其属性的映射表
	 */
	protected Map<Class<?>, Class<?>> getProxyMixInAnnotation(Method method) {
		Map<Class<?>, Class<?>> map = proxydMethodMap.get(method);// 从缓存中查找是否存在

		if (map != null && map.entrySet().size() > 0) {// 如果已经读取该方法的注解信息，则从缓存中读取
			return map;
		} else {
			map = new HashMap<Class<?>, Class<?>>();
		}

		Class<?> clazzOfMethodIn = method.getDeclaringClass();// 方法所在的class

		Map<Class<?>, Collection<String>> pojoAndNamesMap = new HashMap<Class<?>, Collection<String>>();

		IgnoreProperties classIgnoreProperties = clazzOfMethodIn
				.getAnnotation(IgnoreProperties.class);
		IgnoreProperty classIgnoreProperty = clazzOfMethodIn.getAnnotation(IgnoreProperty.class);
		AllowProperty classAllowProperty = clazzOfMethodIn.getAnnotation(AllowProperty.class);

		IgnoreProperties ignoreProperties = method.getAnnotation(IgnoreProperties.class);
		IgnoreProperty ignoreProperty = method.getAnnotation(IgnoreProperty.class);
		AllowProperty allowProperty = method.getAnnotation(AllowProperty.class);

		if (allowProperty != null) {// 方法上的AllowProperty注解
			processAllowPropertyAnnotation(allowProperty, pojoAndNamesMap);
		}
		if (classAllowProperty != null) {
			processAllowPropertyAnnotation(classAllowProperty, pojoAndNamesMap);
		}

		if (classIgnoreProperties != null) {// 类上的IgnoreProperties注解
			processIgnorePropertiesAnnotation(classIgnoreProperties, pojoAndNamesMap);
		}
		if (classIgnoreProperty != null) {// 类上的IgnoreProperty注解
			processIgnorePropertyAnnotation(classIgnoreProperty, pojoAndNamesMap);
		}

		if (ignoreProperties != null) {// 方法上的IgnoreProperties注解
			processIgnorePropertiesAnnotation(ignoreProperties, pojoAndNamesMap);
		}
		if (ignoreProperty != null) {// 方法上的IgnoreProperties注解
			processIgnorePropertyAnnotation(ignoreProperty, pojoAndNamesMap);
		}

		Set<Entry<Class<?>, Collection<String>>> entries = pojoAndNamesMap.entrySet();
		for (Iterator<Entry<Class<?>, Collection<String>>> iterator = entries.iterator(); iterator
				.hasNext();) {
			Entry<Class<?>, Collection<String>> entry = (Entry<Class<?>, Collection<String>>) iterator
					.next();
			Collection<String> nameCollection = entry.getValue();
			nameCollection = putGlobalIgnoreProperties(nameCollection);// 将全局过滤字段放入集合内
			String[] names = nameCollection.toArray(new String[] {});
			Class<?> clazz = createMixInAnnotation(names);
			map.put(entry.getKey(), clazz);
		}
		proxydMethodMap.put(method, map);
		return map;
	}

	/**
	 * 创建jackson的代理注解接口类
	 * @param names
	 *            要生成的字段
	 * @return 代理接口类
	 */
	private Class<?> createMixInAnnotation(String[] names) {
		Class<?> clazz = null;
		clazz = proxyMixInAnnotationMap.get(StringHelper.hashCodeOfStringArray(names));
		if (clazz != null) {
			return clazz;
		}

		ClassPool pool = ClassPool.getDefault();

		// 创建代理接口
		CtClass cc = pool.makeInterface("ProxyMinxinAnnotation" + System.currentTimeMillis()
				+ proxyIndex++);

		ClassFile ccFile = cc.getClassFile();
		ConstPool constpool = ccFile.getConstPool();

		// create the annotation
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool,
				AnnotationsAttribute.visibleTag);
		// 创建JsonIgnoreProperties注解
		Annotation jsonIgnorePropertiesAnnotation = new Annotation(
				JsonIgnoreProperties.class.getName(), constpool);

		BooleanMemberValue ignoreUnknownMemberValue = new BooleanMemberValue(false, constpool);

		ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constpool);// value的数组成员

		Collection<MemberValue> memberValues = new HashSet<MemberValue>();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			StringMemberValue memberValue = new StringMemberValue(constpool);// 将name值设入注解内
			memberValue.setValue(name);
			memberValues.add(memberValue);
		}
		arrayMemberValue.setValue(memberValues.toArray(new MemberValue[] {}));

		jsonIgnorePropertiesAnnotation.addMemberValue("value", arrayMemberValue);
		jsonIgnorePropertiesAnnotation.addMemberValue("ignoreUnknown", ignoreUnknownMemberValue);

		attr.addAnnotation(jsonIgnorePropertiesAnnotation);
		ccFile.addAttribute(attr);

		// generate the class
		try {
			clazz = cc.toClass();
			proxyMixInAnnotationMap.put(StringHelper.hashCodeOfStringArray(names), clazz);
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	@Override
	public void filterProperties(Method method, Object object) {
		Map<Class<?>, Class<?>> map = getProxyMixInAnnotation(method);
		ObjectMapper mapper = createObjectMapper(map);
		try {
			HttpServletResponse response = WebContext.getInstance().getResponse();
			writeJson(mapper, response, object);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 根据指定的过滤表创建jackson对象
	 * @param map
	 *            过滤表
	 * @return ObjectMapper
	 */
	private ObjectMapper createObjectMapper(Map<Class<?>, Class<?>> map) {
		ObjectMapper mapper = new ObjectMapper();
		Set<Entry<Class<?>, Class<?>>> entries = map.entrySet();
		for (Iterator<Entry<Class<?>, Class<?>>> iterator = entries.iterator(); iterator.hasNext();) {
			Entry<Class<?>, Class<?>> entry = (Entry<Class<?>, Class<?>>) iterator.next();
			mapper.addMixInAnnotations(entry.getKey(), entry.getValue());
		}
		return mapper;
	}

	/**
	 * 将结果输出到response
	 * @param objectMapper
	 * @param response
	 * @param object
	 */
	@SuppressWarnings("deprecation")
	private void writeJson(ObjectMapper objectMapper, HttpServletResponse response, Object object) {
		response.setContentType("application/json");
		JsonEncoding encoding = getJsonEncoding(response.getCharacterEncoding());
		JsonGenerator jsonGenerator = null;
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					response.getOutputStream(), encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			jsonGenerator.useDefaultPrettyPrinter();
		}

		try {
			objectMapper.writeValue(jsonGenerator, object);
		} catch (Exception e) {
			throw new RuntimeException("Could not write JSON: " + e.getMessage(), e);
		}
	}
	
	private JsonEncoding getJsonEncoding(String characterEncoding) {
		for (JsonEncoding encoding : JsonEncoding.values()) {
			if (characterEncoding.equals(encoding.getJavaName())) {
				return encoding;
			}
		}
		return JsonEncoding.UTF8;
	}

	protected JsonEncoding getJsonEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			Charset charset = contentType.getCharSet();
			for (JsonEncoding encoding : JsonEncoding.values()) {
				if (charset.name().equals(encoding.getJavaName())) {
					return encoding;
				}
			}
		}
		return JsonEncoding.UTF8;
	}
}
