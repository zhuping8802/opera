package org.ping.core.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * 
 * <p>TypeName: Identities</p>
 * <p>Description: (自动增长)</p>
 * <p>date : 2014年10月16日 下午4:17:06 </p>
 * @author ping
 * @version 1.0
 */
public final class Identities {
	private static Object lock = new Object();
	private static Long seq = 1l;
	private static NumberFormat nf = new DecimalFormat("-000000000000000000");
	
	/**
	 * 最大值
	 */
	private static final Long MAXSEQ = 999999999999999999l;
	
	private Identities(){
		
	}
	
	/**
	 * 
	 * TypeName: Identities
	 * MethodName: getId (获取自增长的ID) 
	 * @author ping
	 * @date 2014年10月16日 下午4:30:23
	 * @return
	 */
	public static String getId(){
		StringBuffer id = new StringBuffer();
		synchronized (lock) {
			if(seq >= MAXSEQ){
				seq = 1l;
			}
			id.append(new Date().getTime());
			id.append(nf.format(seq));
			seq ++;
		}
		return id.toString();
	}
	
	public static void main(String[] args) {
		String id = Identities.getId();
		System.out.println("id = " + id + ";" + id.length());
	}
}
