package org.ping.study.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 输入输出测试
 * @author ping
 *
 */
public class IOTest {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String path = "D:\\JAVA\\PDF\\java线程.pdf";
		File pdf = new File(path);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(pdf));
			bos = new BufferedOutputStream(new FileOutputStream(File.createTempFile("java线程测试", ".pdf")));
			byte[] b = new byte[1024 * 64];
			int flag = 0;
			while((flag = bis.read(b)) != -1){
				bos.write(b);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(bos != null){
					bos.close();
				}
				if(bis != null){
						bis.close();
				}
				System.out.println(System.currentTimeMillis() - start);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
