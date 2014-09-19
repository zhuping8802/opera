package org.ping.chart.service;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.ping.chart.bean.Chart;
import org.ping.chart.util.ChartUtil;


/**
 * 图表操作抽象类
 * @author ping.zhu
 *
 */
public abstract class ChartOperation {

	/**
	 * 生成图表
	 * @return
	 */
	public abstract JFreeChart createChart(Chart chart);
	
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
