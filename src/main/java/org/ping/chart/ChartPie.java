package org.ping.chart;

import org.jfree.data.general.DefaultPieDataset;

/**
 * 饼状图数据对象
 * @author ping.zhu
 *
 */
public class ChartPie extends Chart{
	
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

	public ChartPie(DefaultPieDataset dataset) {
		super();
		this.dataset = dataset != null ? dataset : new DefaultPieDataset();
	}
	
}
