package org.ping.chart;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 柱状图数据对象
 * @author ping.zhu
 *
 */
@SuppressWarnings("rawtypes")
public class ChartBar extends Chart{
	/**
	 * 柱状图统计指标集合，即分组的柱子代表意思
	 * 如：{"工作项", "交付物"}
	 */
	private List rowKeys = null;
	
	/**
	 * 柱状图统计范围集合，即每个分组的名称
	 * 如：{"项目１", "项目２"}
	 */
	private List columnKeys = null;
	
	/**
	 * 柱状图数据集
	 */
	private DefaultCategoryDataset dataset;
	
	/**
	 * 每个柱子宽度
	 */
	private int barWidth = 0;
	
	/**
	 * 柱子最大高度
	 */
	private int barHeight = 0;
	
	public ChartBar(DefaultCategoryDataset dataset) {
		super();
		this.dataset = dataset != null ? dataset : new DefaultCategoryDataset();
		this.columnKeys = dataset.getColumnKeys() != null ? dataset.getColumnKeys() : new ArrayList();
		this.rowKeys = dataset.getRowKeys() != null ? dataset.getRowKeys() : new ArrayList();
	}

	public List getRowKeys() {
		return rowKeys;
	}

	public List getColumnKeys() {
		return columnKeys;
	}

	public DefaultCategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}

	public int getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(int barWidth) {
		this.barWidth = barWidth;
	}

	public int getBarHeight() {
		return barHeight == 0 ? this.getImageHeight() : barHeight;
	}

	public void setBarHeight(int barHeight) {
		this.barHeight = barHeight;
	}

}
