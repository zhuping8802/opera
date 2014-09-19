package org.ping.chart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.ping.chart.bean.Chart;
import org.ping.chart.service.ChartOperation;


/**
 * 图表工具类
 * @author ping.zhu
 *
 */
public class ChartUtil {
	
	/**
	 * png图片
	 */
	public static final String IMAGE_TYPE_PNG = ".png";
	
	/**
	 * jpeg图片
	 */
	public static final String IMAGE_TYPE_JPEG = ".jpge";
	
	/**
	 * 字体
	 */
	public static final String FONT_NAME = "黑体";
	
	/**
	 * 小号字
	 */
	public static final int FONT_SIZE_SMALL = 15;
	
	/**
	 * 大号字
	 */
	public static final int FONT_SIZE_BIG = 20;
	/**
	 * 无数据显示
	 */
	public static final String NO_DATA_MESSAGE = "无数据显示";
	
	/**
	 * 下载图片
	 * @param response
	 * @param chart
	 */
	public static final void exportChart(HttpServletResponse response, Chart chart){
		JFreeChart freeChart = getFreeChart(chart);
		if(freeChart != null){
			exportChart(freeChart, chart.getImageType(), chart.getImageWidth(), chart.getImageHeight(), response);
		}
	}
	
	/**
	 * 导出图片到页面
	 * @param request
	 * @param response
	 * @param chart
	 */
	public static final void exportChartToPage(HttpServletRequest request, HttpServletResponse response, Chart chart){
		JFreeChart freeChart = getFreeChart(chart);
		if(freeChart != null){
			exportChartToPage(freeChart, chart.getImageType(), chart.getImageWidth(), chart.getImageHeight(), request, response);
		}
	}
	
	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	private static JFreeChart getFreeChart(Chart chart){
		JFreeChart freeChart = null;
		try {
			String className = chart.getClass().getName();
			Class target = ChartUtil.class.forName(className + "Operation");
			Constructor constructor = target.getConstructor(chart.getClass());
			ChartOperation chartOperation = (ChartOperation) constructor.newInstance(chart);
			freeChart = chartOperation.createChart(chart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return freeChart;
	}
	
	/**
	 * 导出图片
	 * @param chart
	 * @param imageType
	 * @param imageWidth
	 * @param imageHeight
	 * @param response
	 */
	private static void exportChart(JFreeChart chart, String imageType, int imageWidth, int imageHeight, HttpServletResponse response){
		File image = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			// 生成临时图片
			if(ChartUtil.IMAGE_TYPE_JPEG.equals(imageType)){
				image = File.createTempFile(Identities.getId() + "", ChartUtil.IMAGE_TYPE_JPEG);
				ChartUtilities.saveChartAsJPEG(image, chart, imageWidth, imageHeight);
			}else{
				image = File.createTempFile(Identities.getId() + "", ChartUtil.IMAGE_TYPE_PNG);
				ChartUtilities.saveChartAsPNG(image, chart, imageWidth, imageHeight);
			}
			// 提供下载
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment; filename=".concat(new String(image.getName().getBytes(), "ISO-8859-1")));
			out = response.getOutputStream();
			in = new FileInputStream(image);
			int bufSize = 64 * 1024;
			byte[] buf = new byte[bufSize];
			int nextRead = -1;
			while ((nextRead = in.read(buf)) >  -1){
				out.write(buf, 0, nextRead);
			}
		} catch (IOException e) {
			
		} finally{
			try {
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
			}
			// 删除临时图片
			if(image != null && image.exists()){
				image.delete();
			}
		}
	}
	
	/**
	 * 导入图片并在页面显示
	 * @param chart
	 * @param imageType
	 * @param imageWidth
	 * @param imageHeight
	 * @param request
	 * @param response
	 */
	private static void exportChartToPage(JFreeChart chart, String imageType, int imageWidth, int imageHeight, HttpServletRequest request, HttpServletResponse response){
		String imageName = "";
		try {
			if(ChartUtil.IMAGE_TYPE_JPEG.equals(imageType)){
				imageName = ServletUtilities.saveChartAsJPEG(chart, imageWidth, imageHeight, request.getSession());
			}else{
				imageName = ServletUtilities.saveChartAsPNG(chart, imageWidth, imageHeight, request.getSession());
			}
		} catch (IOException e) {
		}
		String str = "/DisplayChart?filename=" + imageName;
		try {
			request.getRequestDispatcher(str).forward(request, response);
		} catch (ServletException e) {
		} catch (IOException e) {
		}
	}
}
