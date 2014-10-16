package org.ping.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 反射工具类
 * 
 * @author ping.zhu
 *
 */
public final class ReflectUtil {

	private ReflectUtil() {

	}

	/**
	 * 获取对象指定属性字段，包括继承字段，直到找到为止
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {

			}
		}
		return null;
	}

	/**
	 * 获取指定对象，指定方法名及参数的方法，包括继承方法，直到找到为止
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getMethodByMethodName(Object obj, String methodName,
			Class<?> parameterTypes) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {

			}
		}
		return null;
	}

	/**
	 * 获取指定对象某个字段的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置指定对象指定字段属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		if(field != null){
			if (field.isAccessible()) {
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
		}
	}
	
	/**
	 * 获取Class类的所有非静态字段名
	 * 
	 * @param clazz
	 * @return
	 */
	public static Collection<String> getUnstaticClassFieldNameCollection(Class<?> clazz) {
		if (clazz == null) {
			throw new NullPointerException("传入的clazz为空对象！");
		}
		Field[] fields = clazz.getDeclaredFields();
		int length = fields.length;
		Collection<String> fieldNames = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			Field field = fields[i];
			if (!Modifier.isStatic(field.getModifiers())) {
				fieldNames.add(field.getName());
			}
		}
		return fieldNames;
	}
}
