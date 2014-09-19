package org.ping.chart.service;

import java.awt.Font;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.ping.chart.bean.Chart;
import org.ping.chart.util.ChartUtil;
import org.springframework.stereotype.Service;


/**
 * 图表操作抽象类
 * @author ping.zhu
 *
 */
@Service
public abstract class ChartOperation {

	/**
	 * 生成图表
	 * @return
	 */
	public abstract JFreeChart createChart(Chart chart);
	
	/**
	 * 下载图片
	 * @param response
	 * @param chart
	 */
	public final void exportChart(HttpServletResponse response, Chart chart){
		JFreeChart freeChart = this.createChart(chart);
		if(freeChart != null){
			ChartUtil.exportChart(freeChart, chart.getImageType(), chart.getImageWidth(), chart.getImageHeight(), response);
		}
	}
	
	/**
	 * 导出图片到页面
	 * @param request
	 * @param response
	 * @param chart
	 */
	public final void exportChartToPage(HttpServletRequest request, HttpServletResponse response, Chart chart){
		JFreeChart freeChart = this.createChart(chart);
		if(freeChart != null){
			ChartUtil.exportChartToPage(freeChart, chart.getImageType(), chart.getImageWidth(), chart.getImageHeight(), request, response);
		}
	}
	
	static{
		//创建主题样式  
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		//设置标题字体  
		standardChartTheme.setExtraLargeFont(new Font(ChartUtil.FONT_NAME, Font.BOLD, ChartUtil.FONT_SIZE_BIG));
		//设置图例的字体  
		standardChartTheme.setRegularFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_SMALL));
		//设置轴向的字体  
		standardChartTheme.setLargeFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_SMALL));
		standardChartTheme.setShadowVisible(false);
		//应用主题样式  
		ChartFactory.setChartTheme(standardChartTheme);
	}
}
