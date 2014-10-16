package org.ping.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * 字符串工具
 * @author ping.zhu
 *
 */
public class StringHelper {
	/**
	 * 判断字符串不为空，并且字符串内容不为空
	 * 
	 * @param input
	 * @return
	 */
	public static boolean notNullAndNotEmpty(String input) {
		return input != null && !"".equals(input.trim());
	}

	/**
	 * 计算数组的hashCode
	 * 
	 * @param stringArray
	 * @return
	 */
	public static int hashCodeOfStringArray(String[] stringArray) {
		if (stringArray == null) {
			return 0;
		}
		int hashCode = 17;
		for (int i = 0; i < stringArray.length; i++) {
			String value = stringArray[i];
			hashCode = hashCode * 31 + (value == null ? 0 : value.hashCode());
		}
		return hashCode;
	}

	/**
	 * 判断字符串是否为空，或者字符串内容为空
	 * 
	 * @param input
	 * @return
	 */
	public static boolean nullOrEmpty(String input) {
		return input == null || "".equals(input.trim());
	}

	/**
	 * 换行符
	 * 
	 * @return
	 */
	public static String line() {
		String lineSeparator = System.getProperty("line.separator");
		return lineSeparator;
	}

	public static String convertEncode(String str) {
		if (nullOrEmpty(str)) {
			return null;
		}
		try {
			return new String(str.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * StringReader转换为字符串
	 * 
	 * @param reader
	 * @return
	 */
	public String stringReaderToString(StringReader reader) {
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[128];
		int length = -1;
		try {
			while ((length = reader.read(buffer)) != -1) {
				if (buffer.length != length) {
					System.arraycopy(buffer, 0, buffer, 0, length);
				}
				builder.append(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return builder.toString();
	}

	public static String UUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
	}
}
