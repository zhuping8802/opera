package org.ping.chart.bean;

import org.jfree.data.general.DefaultPieDataset;

/**
 * 饼状图数据对象
 * @author ping.zhu
 *
 */
public class ChartPie extends Chart{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6042232307624488531L;

	/**
	 * 柱状图数据集
	 */
	private DefaultPieDataset dataset;
	
	public DefaultPieDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultPieDataset dataset) {
		this.dataset = dataset;
	}

	public ChartPie() {
		super();
	}

	public ChartPie(DefaultPieDataset dataset) {
		super();
		this.dataset = dataset != null ? dataset : new DefaultPieDataset();
	}
	
}
