package org.ping.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 流水号生成器
 * @author ping.zhu
 *
 */
public final class SerialNumber {
	
	private static Object lock = new Object();
	
	// 当前最大的流水
	private static Map<String, Integer> maxSerial = new HashMap<String, Integer>();
	// 可用流水
	private static Map<String, SortedSet<Integer>> avaliableSerial = new HashMap<String, SortedSet<Integer>>();
	// 数字格式化工具缓存器
	private static Map<String, NumberFormat> formatBuffer = new HashMap<String, NumberFormat>();
	private static final String SERIAL_NUMBER_KEY = "serial_number_";
	
	private SerialNumber(){
		
	}
	
	/**
	 * 获取流水号
	 * @param key 流水号版本
	 * @param digit 流水号位数
	 * @return
	 */
	public static String getSerialNumber(String key, int digit){
		synchronized (lock) {
			Integer number = null;
			Integer maxNumber = maxSerial.get(key);
			if(avaliableSerial.containsKey(key)){ // 有可用流水
				SortedSet<Integer> sortedSet = avaliableSerial.get(key);
				if(!sortedSet.isEmpty()){
					number = sortedSet.first();
					sortedSet.remove(number);
				}else{ // 无可用流水
					if(maxNumber != null){
						++maxNumber;
					}else{
						maxNumber = 0;
					}
					number = maxNumber;
				}
			}else{
				if(maxNumber != null){
					++maxNumber;
				}else{
					maxNumber = 0;
				}
				number = maxNumber;
			}
			
			maxSerial.put(key, maxNumber);
			
			// 格式化
			NumberFormat nf = getNumberFormat(digit);
			String serialNumber = nf.format(number);
			
			return serialNumber;
		}
	}
	
	/**
	 * 获取流水号
	 * @param digit 流水号位数
	 * @return
	 */
	public static String getSerialNumber(int digit){
		return getSerialNumber(SERIAL_NUMBER_KEY + digit, digit);
	}
	
	/**
	 * 回收被使用的流水号
	 * @param key 流水号版本
	 * @param serialNumber　待回收的流水号
	 */
	public static void recoverSerivalNumber(String key, String serialNumber){
		SortedSet<Integer> sortedSet = avaliableSerial.get(key);
		Integer number = Integer.parseInt(serialNumber);
		if(sortedSet == null){
			sortedSet = new TreeSet<Integer>();
		}
		sortedSet.add(number);
		avaliableSerial.put(key, sortedSet);
	}
	
	/**
	 * 回收被使用的流水号
	 * @param serialNumber　待回收的流水号
	 */
	public static void recoverSerivalNumber(String serialNumber){
		int digit = serialNumber.length();
		String key = SERIAL_NUMBER_KEY + digit;
		recoverSerivalNumber(key, serialNumber);
	}
	
	/**
	 * 获取NumberFormat
	 * @param digit
	 * @return
	 */
	private static NumberFormat getNumberFormat(int digit){
		NumberFormat format = null;
		if(!formatBuffer.containsKey(digit)){
			StringBuffer patter = new StringBuffer("");
			for(int i = 0; i < digit; i++){
				patter.append("0");
			}
			format = new DecimalFormat(patter.toString());
		}else{
			format = formatBuffer.get(digit);
		}
		return format;
	}

	public static void main(String[] args) {
		for(int i = 0; i < 20; i++){
			System.out.println("================" + SerialNumber.getSerialNumber("key", 4));
		}
		
		SerialNumber.recoverSerivalNumber("key", "0015");
		SerialNumber.recoverSerivalNumber("key", "0007");
		SerialNumber.recoverSerivalNumber("key", "0009");
		SerialNumber.recoverSerivalNumber("key", "0011");
		
		for(int i = 0; i < 10; i++){
			System.out.println("--------------------------" + SerialNumber.getSerialNumber("key", 4));
		}
		
		for(int i = 0; i < 20; i++){
			System.out.println("================" + SerialNumber.getSerialNumber(6));
		}
		
		SerialNumber.recoverSerivalNumber("000015");
		SerialNumber.recoverSerivalNumber("000002");
		SerialNumber.recoverSerivalNumber("000019");
		
		for(int i = 0; i < 10; i++){
			System.out.println("--------------------------" + SerialNumber.getSerialNumber(6));
		}
	}

}
