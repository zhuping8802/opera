package org.ping.chart.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.ping.chart.bean.ChartBar;
import org.ping.chart.bean.ChartPie;
import org.ping.chart.service.ChartBarOperation;
import org.ping.chart.service.ChartPieOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 导出图表控件演示
 * @author ping.zhu
 *
 */
@Controller
@RequestMapping("/chart")
public class ChartDemo {
	
	@Autowired
	private ChartBarOperation chartBarOperation;
	
	@Autowired
	private ChartPieOperation chartPieOperation;
	
	/**
	 * 导出柱状图
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportBarImg")
	public void exportBarImg(HttpServletRequest request, HttpServletResponse response){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(20,"工作项", "项目１" );
		dataset.addValue(0, "交付物", "项目１");
		dataset.addValue(0, "工时", "项目１");
		dataset.addValue(0, "问题报告", "项目１");
//		dataset.addValue(20,"工作项", "项目2" );
//		dataset.addValue(0, "交付物", "项目2");
//		dataset.addValue(57, "工时", "项目2");
//		dataset.addValue(30, "问题报告", "项目3");
		ChartBar bar = new ChartBar(dataset);
		bar.setMainTitle("所有项目的　统计");
		bar.setBarChart3D(true);
		chartBarOperation.exportChart(response, bar);
	}
	
	/**
	 * 导出饼图
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportPieImg")
	public void exportPieImg(HttpServletRequest request, HttpServletResponse response){
		//设置饼图数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("黑心矿难", 720);
		dataset.setValue("醉酒驾驶", 530);
		dataset.setValue("城管强拆", 210);
		dataset.setValue("医疗事故", 91);
		dataset.setValue("其他", 66);
		ChartPie pie = new ChartPie(dataset);
		pie.setBarChart3D(true);
		chartPieOperation.exportChart(response, pie);
	}
}
