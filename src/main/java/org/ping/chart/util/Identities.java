package org.ping.chart.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Id自动增加生成器
 * @author ping.zhu
 *
 */
public final class Identities {
	private static Object lock = new Object();
	private static Long seq = 1l;
	private static NumberFormat nf = new DecimalFormat("-000000000000000000");
	/**
	 * 最大的Sel
	 */
	private static final Long MAXSEQ = 999999999999999999l;
	
	private Identities(){
		
	}
	
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
