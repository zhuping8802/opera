package org.ping.study.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.ping.core.util.StringHelper;

/**
 * 实体帮助类
 * @author ping.zhu
 *
 */
public class EntityHelper {

	public static final Map<Class<?>, Class<?>> baseTypePackagingMap = new HashMap<Class<?>, Class<?>>();
	static {
		baseTypePackagingMap.put(int.class, Integer.class);
		baseTypePackagingMap.put(long.class, Long.class);
		baseTypePackagingMap.put(double.class, Double.class);
		baseTypePackagingMap.put(float.class, Float.class);
		baseTypePackagingMap.put(boolean.class, Boolean.class);
		baseTypePackagingMap.put(char.class, Character.class);
		baseTypePackagingMap.put(byte.class, Byte.class);
		baseTypePackagingMap.put(short.class, Short.class);
		baseTypePackagingMap.put(Integer.class, Integer.class);
		baseTypePackagingMap.put(Long.class, Long.class);
		baseTypePackagingMap.put(Double.class, Double.class);
		baseTypePackagingMap.put(Float.class, Float.class);
		baseTypePackagingMap.put(Boolean.class, Boolean.class);
		baseTypePackagingMap.put(Character.class, Character.class);
		baseTypePackagingMap.put(Byte.class, Byte.class);
		baseTypePackagingMap.put(Short.class, Short.class);

	}

	public static final Object LOCK = new Object();
	private static Map<Class<?>, Object> singletonMap = new HashMap<Class<?>, Object>();

	/**
	 * 获取Class类的所有字段名
	 * 
	 * @param clazz
	 * @return
	 */
	public static String[] getClassFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		int length = fields.length;
		String[] fieldNames = new String[length];
		for (int i = 0; i < length; i++) {
			Field field = fields[i];
			fieldNames[i] = field.getName();
		}
		return fieldNames;
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

	/**
	 * 获取Class类的所有非静态字段名
	 * 
	 * @param clazz
	 * @return
	 */
	public static String[] getUnstaticClassFields(Class<?> clazz) {
		return getUnstaticClassFieldNameCollection(clazz).toArray(new String[] {});
	}
	
	/**
	 * 打印map
	 * 
	 * @param map
	 */
	public static void printMap(Map map){
		StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
		StringBuilder builder = new StringBuilder();
		builder.append(" ------------------------------------------------------------ ");
		builder.append(StringHelper.line());
		builder.append(StackTraceHelper
				.buildStackTrace(new StackTraceElement[] { stackTraceElement }));
		builder.append("    ");
		
		Set<Entry> entries = map.entrySet();
//		StringBuilder builder = new StringBuilder();
		builder.append("map[");
		for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			builder.append(key);
			builder.append("=");
			if(value == null){
				builder.append("<null>");
			} else {
				builder.append(value.toString());
			}
			if(iterator.hasNext()){
				builder.append(", ");
			}
		}
		builder.append("]");
		builder.append(StringHelper.line());
		builder.append(" ------------------------------------------------------------ ");
		System.out.println(builder.toString());
	}

	/**
	 * 获取Class类的所有字段名
	 * 
	 * @param clazz
	 * @return
	 */
	public static String[] getClassSimpleFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		int length = fields.length;
		Collection<Field> fieldCollection = new ArrayList<Field>();

		for (int i = 0; i < length; i++) {
			Field field = fields[i];
			if (isSimpleType(field)) {
				fieldCollection.add(field);
			}
		}
		String[] fieldNames = new String[fieldCollection.size()];
		int i = 0;
		for (Iterator iterator = fieldCollection.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			fieldNames[i] = field.getName();
			i++;
		}
		return fieldNames;
	}

	/**
	 * 获取Class类的单例，如果其他类不是使用的该方法，那么必定造成对象引用不一致！因此一定要检查每个对象的构造方式是否一致都是使用本方法来构造
	 * 
	 * @param clazz
	 *            类型
	 * @param args
	 *            构造函数的参数列表
	 * @return 单例对象
	 * @throws Exception
	 *             异常
	 */
	public static final Object getSingleton(Class<?> clazz, Object... args) throws Exception {
		Object instance = null;
		synchronized (LOCK) {
			instance = singletonMap.get(clazz);
			if (instance == null) {
				Constructor<?> constructor = null;
				try {
					constructor = getConstructor(clazz, args);
				} catch (SecurityException e1) {
				} catch (NoSuchMethodException e1) {
				}
				if (constructor != null) {
					try {
						instance = constructor.newInstance(args);
					} catch (IllegalArgumentException e) {
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
				}
				if (instance == null) {
					Constructor<?>[] constructors = clazz.getConstructors();
					for (int i = 0; i < constructors.length; i++) {
						Constructor<?> constructorVar = constructors[i];
						try {
							instance = constructorVar.newInstance(args);
						} catch (IllegalArgumentException e) {
						} catch (InstantiationException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {
						}
					}
				}
				if (instance == null) {
					throw new NoSuchMethodException("无法初始化对象: " + clazz + " ;请检查传入的参数是否吻合");
				}
			}
			singletonMap.put(clazz, instance);
		}
		return instance;
	}

	/**
	 * 根据传入的给予构造参数的传入值获取构造方法
	 * 
	 * @param clazz
	 *            调用的类对象
	 * @param args
	 *            传入的参数
	 * @return 如果存在，则返回构造方法，如果不存在，则返回空
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Constructor<?> getConstructor(Class<?> clazz, Object... args)
			throws SecurityException, NoSuchMethodException {
		Constructor<?> constructor = null;
		int length = args.length;
		Class<?>[] classes = new Class<?>[length];
		for (int i = 0; i < length; i++) {
			Object object = args[i];
			classes[i] = object.getClass();
		}
		constructor = clazz.getConstructor(classes);
		return constructor;
	}

	/**
	 * 根据Class类、方法名和传入的参数，获取对应的方法对象
	 * 
	 * @param clazz
	 *            Class类
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数
	 * @return Method
	 */
	public static Method getMethod(Class<? extends Object> clazz, String methodName, Object[] args)
			throws SecurityException, NoSuchMethodException {
		Method method = null;
		int length = args.length;
		Class<?>[] classes = new Class<?>[length];
		for (int i = 0; i < length; i++) {
			Object object = args[i];
			classes[i] = object.getClass();
		}
		try {
			method = clazz.getDeclaredMethod(methodName, classes);
		} catch (Exception e) {
		}
		if (method == null && args.length > 0) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method methodVar = methods[i];
				Class<?>[] types = methodVar.getParameterTypes();
				boolean isThisMethod = false;
				if (types.length == args.length) {
					isThisMethod = true;
					for (int j = 0; j < types.length && isThisMethod; j++) {
						Class<?> type = types[j];
						Object parameter = args[j];
						if (!typeEquals(type, parameter)) {
							isThisMethod = false;
						}
					}
				}
				if (isThisMethod) {
					method = methodVar;
					break;
				}
			}
		}
		return method;
	}

	/**
	 * 根据字段获取Class类的get方法
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 */
	public static Method getMethodOfBeanByField(Class<?> clazz, Field field) {
		Method method = prefixMethodOfBeanByField("get", clazz, field);
		if (method == null
				&& (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class))) {
			method = prefixMethodOfBeanByField("is", clazz, field);
		}
		return method;
	}

	/**
	 * 根据字段获取Class类的set方法
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 */
	public static Method setMethodOfBeanByField(Class<?> clazz, Field field) {
		return prefixMethodOfBeanByField("set", clazz, field, field.getType());
	}

	/**
	 * 根据字段获取Class类的set方法
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 */
	public static Method prefixMethodOfBeanByField(String prefix, Class<?> clazz, Field field,
			Class<?>... argTypes) {
		Method method = null;
		String fieldName = field.getName();
		StringBuilder builder = new StringBuilder(prefix);
		builder.append(fieldName.substring(0, 1).toUpperCase());
		builder.append(fieldName.substring(1, fieldName.length()));
		String methodName = builder.toString();
		try {
			method = clazz.getDeclaredMethod(methodName, argTypes);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		return method;
	}

	private static String reflectToString2(Object object) {
		if (object == null) {
			return null;
		}
		Class clazz = object.getClass();
		Class superClazz = clazz.getSuperclass();

		StringBuilder builder = new StringBuilder(clazz.getName());
		builder.append("@");
		builder.append(object.hashCode());
		builder.append("[");
		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = superClazz.getDeclaredFields();

		int length = fields.length;
		int countLength = length;
		for (int i = 0; i < length; i++) {
			Field field = fields[i];
			if (Modifier.isStatic(field.getModifiers())) {
				countLength--;
				continue;
			}
			String fieldName = field.getName();
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			try {
				Method method = clazz.getDeclaredMethod(methodGet);
				try {
					Object value = method.invoke(object);
					builder.append(fieldName);
					builder.append("=");
					if (value == null) {
						builder.append("<null>");
					} else {
						if (value.getClass().isArray()) {
							int arrayLength = Array.getLength(value);
							builder.append("{");
							for (int j = 0; j < arrayLength; j++) {
								Object object2 = Array.get(value, j);
								builder.append(object2.toString());
								if (j < arrayLength - 1) {
									builder.append(", ");
								}
							}
							builder.append("}");
						} else {
							builder.append(value.toString());
						}
					}
					if (i < countLength - 1) {
						builder.append(", ");
					}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}

		int superLength = superFields.length;
		countLength = superLength;
		for (int i = 0; i < superLength; i++) {
			Field field = superFields[i];
			if (Modifier.isStatic(field.getModifiers())) {
				countLength--;
				continue;
			}
			if (countLength > 0) {
				builder.append(", ");
			}
			String fieldName = field.getName();
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			try {
				Method method = superClazz.getDeclaredMethod(methodGet);
				try {
					Object value = method.invoke(object);
					builder.append(fieldName);
					builder.append("=");
					if (value == null) {
						builder.append("<null>");
					} else {
						if (value.getClass().isArray()) {
							int arraysuperLength = Array.getLength(value);
							builder.append("{");
							for (int j = 0; j < arraysuperLength; j++) {
								Object object2 = Array.get(value, j);
								builder.append(object2.toString());
								if (j < arraysuperLength - 1) {
									builder.append(", ");
								}
							}
							builder.append("}");
						} else {
							builder.append(value.toString());
						}
					}
					if (i < countLength - 1) {
						builder.append(", ");
					}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}

		builder.append("]");
		return builder.toString();
	}

	/**
	 * 获取类的所有超类
	 * 
	 * @return
	 */
	public static Collection<Class> getAllSuperClassesOfClass(Class clazz) {
		Collection<Class> classes = new LinkedHashSet<Class>();
		classes.add(clazz);
		Class superClass = clazz.getSuperclass();
		while (superClass != null) {
			classes.add(superClass);
			superClass = superClass.getSuperclass();
		}
		return classes;
	}

	/**
	 * 使用反射机制自动ToString
	 * 
	 * @param object
	 * @return
	 */
	public static String reflectToString(Object object) {
		if (object == null) {
			return null;
		}
		Class clazz = object.getClass();

		StringBuilder builder = new StringBuilder(clazz.getName());
		builder.append("@");
		builder.append(Integer.toHexString(object.hashCode()));
		builder.append("[");

		Set<Method> methods = new LinkedHashSet<Method>();

		Collection<Class> classes = getAllSuperClassesOfClass(clazz);
		for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
			Class claxx = (Class) iterator.next();
			Method[] clazzMethods = claxx.getDeclaredMethods();
			for (int i = 0; i < clazzMethods.length; i++) {
				Method method = clazzMethods[i];
				methods.add(method);
			}
		}

		// for (int i = 0; i < methods.length; i++) {
		// Method method = methods[i];
		for (Iterator iterator = methods.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			String methodName = method.getName();
			if (methodName.startsWith("get") && !Modifier.isStatic(method.getModifiers())
					&& Modifier.isPublic(method.getModifiers())) {
				try {
					Object value = method.invoke(object);
					String propertyName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
					builder.append(propertyName);
					builder.append("=");

					if (value == null) {
						builder.append("<null>");
					} else {
						if (value.getClass().isArray()) {
							int arraysuperLength = Array.getLength(value);
							builder.append("{");
							for (int j = 0; j < arraysuperLength; j++) {
								Object object2 = Array.get(value, j);
								builder.append(object2.toString());
								if (j < arraysuperLength - 1) {
									builder.append(", ");
								}
							}
							builder.append("}");
						} else {
							builder.append(value.toString());
						}
					}
					builder.append(", ");
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			}
		}

		if (builder.toString().contains(", ")) {
			builder.replace(builder.length() - 2, builder.length(), "");
		}

		builder.append("]");
		return builder.toString();
	}

	/**
	 * 简单的ToString方法，只会查找简单类型的数据
	 * 
	 * @param object
	 * @return
	 */
	public static String simpleReflectToString(Object object) {
		if (object == null) {
			return null;
		}
		Class clazz = object.getClass();
		Class superClazz = clazz.getSuperclass();

		StringBuilder builder = new StringBuilder(clazz.getName());
		builder.append("@");
		builder.append(Integer.toHexString(object.hashCode()));
		builder.append("[");

		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (!isSimpleType(method.getReturnType())) {
				continue;
			}
			String methodName = method.getName();
			if (methodName.startsWith("get") && !Modifier.isStatic(method.getModifiers())
					&& Modifier.isPublic(method.getModifiers())) {
				try {
					Object value = method.invoke(object);

					String propertyName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
					builder.append(propertyName);
					builder.append("=");

					if (value == null) {
						builder.append("<null>");
					} else {
						if (value.getClass().isArray()) {
							int arraysuperLength = Array.getLength(value);
							builder.append("{");
							for (int j = 0; j < arraysuperLength; j++) {
								Object object2 = Array.get(value, j);
								builder.append(object2.toString());
								if (j < arraysuperLength - 1) {
									builder.append(", ");
								}
							}
							builder.append("}");
						} else {
							builder.append(value.toString());
						}
					}
					builder.append(", ");
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			}
		}
		if (builder.toString().contains(", ")) {
			builder.replace(builder.length() - 2, builder.length(), "");
		}

		builder.append("]");
		return builder.toString();
	}

	/**
	 * 简单的ToString方法，只会查找简单类型的数据
	 * 
	 * @param object
	 * @return
	 */
	private static String simpleReflectToString2(Object object) {
		if (object == null) {
			return null;
		}
		Class clazz = object.getClass();
		Class superClazz = clazz.getSuperclass();

		StringBuilder builder = new StringBuilder(clazz.getName());
		builder.append("@");
		builder.append(Integer.toHexString(object.hashCode()));
		builder.append("[");
		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = superClazz.getDeclaredFields();

		int length = fields.length;
		int countLength = length;
		for (int i = 0; i < length; i++) {
			Field field = fields[i];
			if (Modifier.isStatic(field.getModifiers()) || !isSimpleType(field)) {
				countLength--;
				continue;
			}
			String fieldName = field.getName();
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			try {
				Method method = clazz.getDeclaredMethod(methodGet);
				try {
					Object value = method.invoke(object);
					builder.append(fieldName);
					builder.append("=");
					if (value == null) {
						builder.append("<null>");
					} else {
						if (value.getClass().isArray()) {
							int arrayLength = Array.getLength(value);
							builder.append("{");
							for (int j = 0; j < arrayLength; j++) {
								Object object2 = Array.get(value, j);
								builder.append(object2.toString());
								if (j < countLength - 1) {
									builder.append(", ");
								}
							}
							builder.append("}");
						} else {
							builder.append(value.toString());
						}
					}
					builder.append(", ");
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}
		if (builder.toString().contains(", ")) {
			builder.replace(builder.length() - 2, builder.length(), "");
		}

		int superLength = superFields.length;
		countLength = superLength;
		for (int i = 0; i < superLength; i++) {
			Field field = superFields[i];
			if (Modifier.isStatic(field.getModifiers()) || !isSimpleType(field)) {
				countLength--;
				continue;
			}
			if (countLength > 0) {
				builder.append(", ");
			}
			String fieldName = field.getName();
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			try {
				Method method = superClazz.getDeclaredMethod(methodGet);
				try {
					Object value = method.invoke(object);
					builder.append(fieldName);
					builder.append("=");
					if (value == null) {
						builder.append("<null>");
					} else {
						if (value.getClass().isArray()) {
							int arraysuperLength = Array.getLength(value);
							builder.append("{");
							for (int j = 0; j < arraysuperLength; j++) {
								Object object2 = Array.get(value, j);
								builder.append(object2.toString());
								if (j < arraysuperLength - 1) {
									builder.append(", ");
								}
							}
							builder.append("}");
						} else {
							builder.append(value.toString());
						}
					}
					if (i < countLength - 1) {
						builder.append(", ");
					}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}

		builder.append("]");
		return builder.toString();

	}

	public static boolean typeEquals(Class<?> type, Object parameter) {
		Class<?> paramenterClass = parameter.getClass();
		if (isSimpleType(paramenterClass) && isSimpleType(type)
				&& getPackagingType(type).asSubclass(getPackagingType(paramenterClass)) != null) {
			return true;
		} else if (type.isInstance(parameter)) {
			return true;
		} else {
			return false;
		}
	}

	public static Class<?> getPackagingType(Class<?> clazz) {
		return baseTypePackagingMap.get(clazz);
	}

	public static boolean isSimpleType(Field field) {
		if (field == null) {
			return false;
		}
		return isSimpleType(field.getType());
	}

	public static boolean isSimpleType(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		// System.out.println(field.getType().getName());

		if (clazz.equals(Boolean.class)) {
			return true;
		} else if (clazz.equals(Float.class)) {
			return true;
		} else if (clazz.equals(Long.class)) {
			return true;
		} else if (clazz.equals(Double.class)) {
			return true;
		} else if (clazz.equals(Byte.class)) {
			return true;
		} else if (clazz.equals(Integer.class)) {
			return true;
		} else if (clazz.equals(Short.class)) {
			return true;
		} else if (clazz.equals(Character.class)) {
			return true;
		} else if (clazz.equals(BigInteger.class)) {
			return true;
		} else if (clazz.equals(BigDecimal.class)) {
			return true;
		} else if (clazz.equals(String.class)) {
			return true;
		} else if (clazz.equals(boolean.class)) {
			return true;
		} else if (clazz.equals(float.class)) {
			return true;
		} else if (clazz.equals(long.class)) {
			return true;
		} else if (clazz.equals(double.class)) {
			return true;
		} else if (clazz.equals(byte.class)) {
			return true;
		} else if (clazz.equals(int.class)) {
			return true;
		} else if (clazz.equals(short.class)) {
			return true;
		} else if (clazz.equals(char.class)) {
			return true;
		}
		return false;
	}

	/**
	 * 计算对象的hashCode
	 * 
	 * @param object
	 * @return
	 */
	public static int hashCode(Object object) {
		if (object == null) {
			return 0;
		}

		int hashCode = 17;
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			String fieldName = field.getName();
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			try {
				Method method = clazz.getDeclaredMethod(methodGet);
				try {
					Object value = method.invoke(object); // 拥有get方法的参数
					hashCode = hashCode * 31 + (value == null ? 0 : value.hashCode());
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			} catch (Exception e) {
			}
		}

		Class<?> superClazz = clazz.getSuperclass();
		Field[] superFields = superClazz.getDeclaredFields();
		for (int i = 0; i < superFields.length; i++) {
			Field field = superFields[i];
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			String fieldName = field.getName();
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());
			try {
				Method method = superClazz.getDeclaredMethod(methodGet);
				try {
					Object value = method.invoke(object); // 拥有get方法的参数
					hashCode = hashCode * 31 + (value == null ? 0 : value.hashCode());
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			} catch (Exception e) {
			}
		}

		return hashCode;
	}

	/**
	 * 比较两个对象
	 * 
	 * @param origin
	 * @param target
	 * @return
	 */
	public static boolean equals(Object origin, Object target) {
		boolean isEquals = false;
		if (origin == null) {
			if (target == null) {
				isEquals = true;
			} else {
				isEquals = false;
			}
		} else {
			if (target == null) {
				isEquals = false;
			} else { // 两者都不为空
				if (origin.getClass() == target.getClass()) {
					if (origin.hashCode() == target.hashCode()) {
						isEquals = true;
					} else {
						isEquals = false;
					}
				} else {
					isEquals = false;
				}
			}
		}
		return isEquals;
	}

	/**
	 * 打印对象
	 * 
	 * @param object
	 */
	public static void printDetail(Object object) {
		StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
		StringBuilder builder = new StringBuilder();
		builder.append(" ------------------------------------------------------------ ");
		builder.append(StringHelper.line());
		builder.append(StackTraceHelper
				.buildStackTrace(new StackTraceElement[] { stackTraceElement }));
		builder.append("    ");
		if (object == null) {
			builder.append("<null>");
		} else {
			builder.append(object.getClass().getSimpleName());
			builder.append(" =============== ");
			builder.append(reflectToString(object));
		}
		builder.append(StringHelper.line());
		builder.append(" ------------------------------------------------------------ ");
		System.out.println(builder.toString());
	}

	/**
	 * 打印对象
	 * 
	 * @param object
	 */
	public static void print(Object object) {
		StackTraceElement[] stackTraceElements = StackTraceHelper.getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[2]; // 调用本类的对象类型堆栈
		StringBuilder builder = new StringBuilder();
		builder.append(" ------------------------------------------------------------ ");
		builder.append(StringHelper.line());
		builder.append(StackTraceHelper
				.buildStackTrace(new StackTraceElement[] { stackTraceElement }));
		builder.append("    ");
		if (object == null) {
			builder.append("<null>");
		} else {
			builder.append(object.getClass().getSimpleName());
			builder.append(" =============== ");
			builder.append(object.toString());
		}
		builder.append(StringHelper.line());
		builder.append(" ------------------------------------------------------------ ");
		System.out.println(builder.toString());
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return equals(this, obj);
	}

	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hashCode(this);
	}

	/**
	 *
	 * @return
	 */
	private String reflectToString() {
		return reflectToString(this);
	}

	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.reflectToString();
	}

	static class A {
		int a;
		int b;
		int c;
		String d;
		boolean e;

		public A(int a, int b, int c, String d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}

		/**
		 * boolean
		 * 
		 * @return the e
		 */
		public boolean isE() {
			return this.e;
		}

		/**
		 * boolean
		 * 
		 * @param e
		 *            the e to set
		 */
		public void setE(boolean e) {
			this.e = e;
		}

		/**
		 * int
		 * 
		 * @return the a
		 */
		public int getA() {
			return this.a;
		}

		/**
		 * int
		 * 
		 * @param a
		 *            the a to set
		 */
		public void setA(int a) {
			this.a = a;
		}

		/**
		 * int
		 * 
		 * @return the b
		 */
		public int getB() {
			return this.b;
		}

		/**
		 * int
		 * 
		 * @param b
		 *            the b to set
		 */
		public void setB(int b) {
			this.b = b;
		}

		/**
		 * int
		 * 
		 * @return the c
		 */
		public int getC() {
			return this.c;
		}

		/**
		 * int
		 * 
		 * @param c
		 *            the c to set
		 */
		public void setC(int c) {
			this.c = c;
		}

		/**
		 * String
		 * 
		 * @return the d
		 */
		public String getD() {
			return this.d;
		}

		/**
		 * String
		 * 
		 * @param d
		 *            the d to set
		 */
		public void setD(String d) {
			this.d = d;
		}

	}

	public static void main(String[] args) {
		//		A a = new A();
		//		a.setA(124);
		//		a.setB(2);
		//		a.setD("asfasf");
		//		printDetail(a);

		//		for (int i = 0; i < 100; i++) {
		//			Thread thread = new Thread() {
		//				/**
		//				 *
		//				 * 2013-10-21 上午11:20:20
		//				 * 
		//				 * @see java.lang.Thread#run()
		//				 */
		//				@Override
		//				public void run() {
		//					try {
		//						A a = (A) EntityHelper.getSingleton(A.class, 1, 2, 3,
		//								"ffff");
		//						System.out.println(a);
		//					} catch (Exception e) {
		//						e.printStackTrace();
		//					}
		//				}
		//			};
		//			thread.start();
		//		}
		try {
			Method method = EntityHelper.getMethodOfBeanByField(A.class,
					A.class.getDeclaredField("e"));
			Method method2 = EntityHelper.setMethodOfBeanByField(A.class,
					A.class.getDeclaredField("e"));
			System.out.println(method);
			System.out.println(method2);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		//		A a;
		//		try {
		//			a = (A) EntityHelper.getSingleton(A.class, 1, 2, 3, "ffff", 1234);
		//			System.out.println(a);
		//			System.out.println(EntityHelper.getSingleton(A.class, 1, 2, 3, "ffff"));
		//			System.out.println(EntityHelper.getSingleton(A.class, 1, 2, 3, "ffff"));
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

}
