package org.ping.chart.service;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.ping.chart.bean.Chart;
import org.ping.chart.bean.ChartPie;
import org.ping.chart.util.ChartUtil;


public class ChartPieOperation extends ChartOperation {
	private ChartPie pie;

	public ChartPieOperation(ChartPie pie) {
		super();
		this.pie = pie;
	}

	public ChartPie getPie() {
		return pie;
	}

	public void setPie(ChartPie pie) {
		this.pie = pie;
	}

	@Override
	public JFreeChart createChart(Chart chart) {
		ChartPie pie = (ChartPie) chart;
		//取得数据集
		DefaultPieDataset dataset = pie.getDataset();
		//通过工厂类生成JFreeChart对象
		JFreeChart freeChart = null;
		if(pie.isBarChart3D()){
			freeChart = ChartFactory.createPieChart3D(pie.getMainTitle(), dataset, true, true, false);
			// 设置背景色
			PiePlot3D pieplot3d = (PiePlot3D)freeChart.getPlot();
			pieplot3d.setBackgroundPaint(Color.WHITE);
			//设置开始角度
			pieplot3d.setStartAngle(120D);
			//设置方向为”顺时针方向“
			pieplot3d.setDirection(Rotation.CLOCKWISE);
			//设置透明度，0.5F为半透明，1为不透明，0为全透明
//			pieplot3d.setForegroundAlpha(0.7F);
		}else{
			freeChart = ChartFactory.createPieChart(pie.getMainTitle(), dataset, true, true, false);
		}
		freeChart.addSubtitle(new TextTitle(pie.getSubTitle()));
		freeChart.setBackgroundPaint(Color.WHITE);
		
		//设置标题字体 
		TextTitle textTitle = freeChart.getTitle(); 
		if(textTitle != null){
			textTitle.setFont(new Font(ChartUtil.FONT_NAME, Font.PLAIN, ChartUtil.FONT_SIZE_BIG)); 
		}
		
		PiePlot pieplot = (PiePlot) freeChart.getPlot();
		pieplot.setLabelFont(new Font(ChartUtil.FONT_NAME, 0, ChartUtil.FONT_SIZE_SMALL));
		pieplot.setOutlinePaint(Color.BLUE);
		
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		pieplot.setLabelGenerator(standarPieIG);

		//没有数据的时候显示的内容
		pieplot.setNoDataMessage(ChartUtil.NO_DATA_MESSAGE);
		pieplot.setLabelGap(0.02D);
		pieplot.setBackgroundPaint(Color.WHITE);
		
		return freeChart;
	}

	public ChartPieOperation() {
		super();
	}
}
