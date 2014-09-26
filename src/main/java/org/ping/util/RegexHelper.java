package org.ping.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则
 * @author ping.zhu
 *
 */
public class RegexHelper {

	public static String replaceAllHTMLComment(String content) {
		Pattern pattern = Pattern.compile("<\\w+.*?>.*?</\\w+.*?>");
		Matcher matcher = pattern.matcher(content);
		String rs = matcher.replaceAll("");
		return rs;
	}

}
