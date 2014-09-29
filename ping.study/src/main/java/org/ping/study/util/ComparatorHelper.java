package org.ping.study.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 比较助手
 * @author ping.zhu
 *
 * @param <T>
 */
public class ComparatorHelper<T> {
	
	/**
	 * 按照Number进行排序
	 * 
	 * @param collection
	 *            要排序的集合
	 * @param fieldName
	 *            指定排序的Number字段名
	 * @param asc
	 *            是否按正序排序
	 * @return List
	 */
	public List<T> sortByNumber(Collection<? extends T> collection, final String fieldName, final boolean asc) {
		List<T> list = new ArrayList<T>();
		list.addAll(collection);
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				Object object = null;
				Object object2 = null;
				try {
					object = ReflectUtil.getValueByFieldName(o1, fieldName);
					object2 = ReflectUtil.getValueByFieldName(o2, fieldName);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (object == null || object2 == null) {
					return 0;
				}
				int result = 0;
				if (object instanceof Number && object2 instanceof Number) {
					Number number1 = (Number) object;
					Number number2 = (Number) object2;
					if (number1.hashCode() < number2.hashCode()) {
						result = -1;
					} else if (number1.hashCode() > number2.hashCode()) {
						result = 1;
					}
					if (!asc) {
						result = -result;
					}
				}
				return result;
			}
		});
		return list;
	}
	
	/**
	 * 按照日期进行排序
	 * 
	 * @param collection
	 *            要排序的集合
	 * @param fieldName
	 *            指定排序的时间字段名
	 * @param asc
	 *            是否按正序排序
	 * @return List
	 */
	public List<T> sortByDateTime(Collection<? extends T> collection, final String fieldName, final boolean asc) {
		if (collection == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		list.addAll(collection);
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				Object object = null;
				Object object2 = null;
				try {
					object = ReflectUtil.getValueByFieldName(o1, fieldName);
					object2 = ReflectUtil.getValueByFieldName(o2, fieldName);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (object == null || object2 == null) {
					return 0;
				}
				int value = 0;
				if (object instanceof Date && object2 instanceof Date) {
					Date v1 = (Date) object;
					Date v2 = (Date) object2;

					if (v1.getTime() < v2.getTime()) {
						value = -1;
					} else if (v1.getTime() > v2.getTime()) {
						value = 1;
					}
					if (!asc) {
						value = -value;
					}
				}
				return value;
			}

		});
		return list;
	}

	/**
	 * 按照字符串进行排序
	 * 
	 * @param collection
	 *            要排序的集合
	 * @param fieldName
	 *            指定排序的时间字段名
	 * @param asc
	 *            是否按正序排序
	 * @return List
	 */
	public Collection<T> sortByString(Collection<? extends T> collection, final String fieldName, final boolean asc) {
		if (collection == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		list.addAll(collection);
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				Object object = null;
				Object object2 = null;
				try {
					object = ReflectUtil.getValueByFieldName(o1, fieldName);
					object2 = ReflectUtil.getValueByFieldName(o2, fieldName);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (object == null || object2 == null) {
					return 0;
				}
				int value = 0;
				if (object instanceof String && object2 instanceof String) {
					String v1 = (String) object;
					String v2 = (String) object2;
					if (v1.hashCode() < v2.hashCode()) {
						value = -1;
					} else if(v1.hashCode() > v2.hashCode()){
						value = 1;
					}
					if (!asc) {
						value = -value;
					}
				}
				return value;
			}
		});
		return list;
	}
	
	/**
	 * 按照字段进行排序
	 * 
	 * @param collection
	 *            要排序的集合
	 * @param fieldName
	 *            指定排序字段名
	 * @param asc
	 *            是否按正序排序
	 * @return List
	 */
	public Collection<T> sortByFiled(Collection<? extends T> collection, final String fieldName, final boolean asc) {
		if (collection == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		list.addAll(collection);
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				Object object = null;
				Object object2 = null;
				try {
					object = ReflectUtil.getValueByFieldName(o1, fieldName);
					object2 = ReflectUtil.getValueByFieldName(o2, fieldName);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (object == null || object2 == null) {
					return 0;
				}
				int value = 0;
				if (object.hashCode() < object2.hashCode()) {
					value = -1;
				} else if(object.hashCode() > object2.hashCode()){
					value = 1;
				}
				if (!asc) {
					value = -value;
				}
				return value;
			}
		});
		return list;
	}

	static class Test {
		private String name;
		private Date date;
		private Integer number;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
	}

	public static void main(String[] args) {
		Random random = new Random();
		Collection<Test> collection = new ArrayList<Test>();
		long now = new Date().getTime();
		for (int i = 0; i < 100; i++) {
			int randomNumber = random.nextInt(1000);
			String name = randomNumber + "";
			Test test = new Test();
			test.setName(name);
			test.setNumber(randomNumber);
			test.setDate(new Date(now + random.nextInt(10000) * 1000));
			collection.add(test);
		}
		ComparatorHelper<Test> comparatorHelper = new ComparatorHelper<Test>();
		
//		collection = comparatorHelper.sortByString(collection,"name", false);
//		collection = comparatorHelper.sortByDateTime(collection,"date", true);
		collection = comparatorHelper.sortByFiled(collection,"date", false);
//		collection = comparatorHelper.sortByNumber(collection,"number", true);
		for (Iterator<Test> iterator = collection.iterator(); iterator.hasNext();) {
			Test test = iterator.next();
			System.out.println(test.getName() + ";;" + test.getDate() + ";;" + test.getNumber());
		}
	}
}
