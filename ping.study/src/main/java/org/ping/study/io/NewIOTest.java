package org.ping.study.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NewIOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		String path = "D:\\JAVA\\PDF\\java线程.pdf";
		File file = new File(path);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(File.createTempFile("java", ".pdf"));
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 64);
			int b = 0;
			while(b != -1){
				buffer.clear();
				b = inChannel.read(buffer);
				buffer.flip();
				outChannel.write(buffer);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(outChannel != null){
					outChannel.close();
				}
				if(fos != null){
					fos.close();
				}
				if(inChannel != null){
					inChannel.close();
				}
				if(fis != null){
						fis.close();
				}
				System.out.println(System.currentTimeMillis() - start);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
