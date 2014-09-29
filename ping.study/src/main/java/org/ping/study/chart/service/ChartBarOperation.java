package org.ping.study.chart.service;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.ping.study.chart.bean.Chart;
import org.ping.study.chart.bean.ChartBar;
import org.ping.study.chart.util.ChartUtil;
import org.springframework.stereotype.Service;

@Service
public class ChartBarOperation extends ChartOperation {

	@Override
	public JFreeChart createChart(Chart chart) {
		ChartBar bar = (ChartBar) chart;
		//设置柱子的颜色
		BarRenderer barRenderer = null;
		if(bar.isBarChart3D()){
			barRenderer = new BarRenderer3D();
		}else{
			barRenderer = new BarRenderer();
		}
		barRenderer.setSeriesPaint(0, new Color(65, 105, 225));
		barRenderer.setSeriesOutlinePaint(0, new Color(65, 105, 225));
		barRenderer.setSeriesPaint(1, new Color(178, 34, 34));
		barRenderer.setSeriesOutlinePaint(1, new Color(178, 34, 34));
		barRenderer.setSeriesPaint(2, new Color(190, 190, 190));
		barRenderer.setSeriesOutlinePaint(2, new Color(190, 190, 190));
		barRenderer.setSeriesPaint(3, new Color(0, 255, 0));
		barRenderer.setSeriesOutlinePaint(3, new Color(0, 255, 0));
		// 设置柱子间距
		barRenderer.setItemMargin(0.15);
		// 去阴影
		barRenderer.setShadowVisible(false);
		barRenderer.setBaseOutlinePaint(Color.BLACK); 
	  	barRenderer.setDrawBarOutline(true);
		
		CategoryDataset dataset = bar.getDataset();
		JFreeChart freeChart = null;
		if(bar.isBarChart3D()){
			freeChart = ChartFactory.createBarChart3D(bar.getMainTitle(), null,
					null, dataset, PlotOrientation.VERTICAL, true, true, false);
		}else{
			freeChart = ChartFactory.createBarChart(bar.getMainTitle(), null,
					null, dataset, PlotOrientation.VERTICAL, true, true, false);
		}
		if(bar.getSubTitle() != null && !"".equals(bar.getSubTitle())){
			freeChart.addSubtitle(new TextTitle(bar.getSubTitle()));
		}
		// 设置背景色
		freeChart.setBackgroundPaint(Color.WHITE);		
		//chart.setBorderVisible(true);
		//chart.setBorderPaint(Color.BLUE);	
		CategoryPlot plot = freeChart.getCategoryPlot();
		plot.setRenderer(barRenderer);
		plot.setBackgroundPaint(Color.WHITE);
		
		// 设置网格竖线
		//plot.setDomainGridlinePaint(Color.GRAY); 
	  	//plot.setDomainGridlinesVisible(true);
	  	// 设置网格横线
	  	plot.setRangeGridlinePaint(Color.GRAY); 
	  	plot.setRangeGridlinesVisible(true); 
	  	plot.setOutlinePaint(Color.BLUE);
	  	plot.setNoDataMessage(ChartUtil.NO_DATA_MESSAGE);
	  	plot.setNoDataMessageFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_BIG));
	  	
	  	//设置标题字体 
		TextTitle textTitle = freeChart.getTitle(); 
		if(textTitle != null){
			textTitle.setFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_BIG)); 
		}
		CategoryAxis domainAxis = plot.getDomainAxis(); 
		//对Y轴做操作 
		ValueAxis rAxis = plot.getRangeAxis(); 
//		CategoryAxis axis = plot.getDomainAxis();
//		double margin = bar.getBarWidth() / (bar.getBarWidth() * bar.getRowKeys().size() * bar.getColumnKeys().size() * 1.0);
//		axis.setLowerMargin(margin);
//		axis.setUpperMargin(margin);
		
		//设置X轴坐标上的文字 
		domainAxis.setTickLabelFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_SMALL)); 
		//设置X轴的标题文字 
		domainAxis.setLabelFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_SMALL)); 
		//设置Y轴坐标上的文字 
		rAxis.setTickLabelFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_SMALL)); 
		//设置Y轴的标题文字 
		rAxis.setLabelFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_SMALL));      
		//底部汉字乱码的问题  
		freeChart.getLegend().setItemFont(new Font(ChartUtil.FONT_NAME,Font.PLAIN,ChartUtil.FONT_SIZE_SMALL)); 
		
		//重新计算柱状图图形大小
		int width = bar.getBarWidth() * bar.getRowKeys().size() * bar.getColumnKeys().size();
		int height = bar.getBarHeight();
		width = width <= 0 ? bar.getImageWidth() : width;
		height = height <= 0 ? bar.getImageHeight() : height;
		bar.setImageHeight(height);
		bar.setImageWidth(width);
		return freeChart;
	}
}
