package org.ping.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩打包文件
 * 
 * @author ping
 *
 */
public final class ZipFiles {
	
	private ZipFiles() {

	}

	/**
	 * 压缩打包文件
	 * 
	 * @param inputFileName
	 *            被打包的文件或目录
	 * @param outputFileName
	 *            打包后的文件
	 * @throws Exception
	 */
	public static void zip(String inputFileName, String outputFileName)
			throws Exception {
		File outfile = new File(outputFileName);
		if(!outfile.exists()){
			outfile.getParentFile().mkdirs();
		}
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outfile));
		zip(zipOut, new File(inputFileName), "");
		if (zipOut != null) {
			zipOut.close();
		}
	}

	private static void zip(ZipOutputStream zipOut, File file, String base)
			throws Exception {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			zipOut.putNextEntry(new ZipEntry(base + File.separator));
			base = base.length() == 0 ? "" : base + File.separator;
			for (int i = 0; i < listFiles.length; i++) {
				zip(zipOut, listFiles[i], base + listFiles[i].getName());
			}
		} else {
			zipOut.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(file);
			int b = -1;
			while ((b = in.read()) != -1) {
				zipOut.write(b);
			}
			in.close();
		}
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String path = System.getProperty("java.class.path");
		String srcDirectoryPath = path;
		String targetFilePath = "D:\\testzip\\test.rar";
		ZipFiles.zip(srcDirectoryPath, targetFilePath);
		System.out.println("打包成功,耗时：" + (System.currentTimeMillis() - start));
	}
}
