package org.ping.chart;

/**
 * 图表公用类
 * @author ping.zhu
 *
 */
public class Chart {

	/**
	 * 显示主标题
	 */
	private String mainTitle = "";
	
	/**
	 * 显示子标题
	 */
	private String subTitle = "";
	
	/**
	 * 是否支持3D
	 */
	private boolean barChart3D = false;
	
	/**
	 * 图片宽度
	 */
	private int imageWidth = 800;
	
	/**
	 * 图片高度
	 */
	private int imageHeight = 500;
	
	/**
	 * 导出图片类型，从ChartUtil中选择
	 */
	private String imageType = ChartUtil.IMAGE_TYPE_PNG;
	
	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public boolean isBarChart3D() {
		return barChart3D;
	}

	public void setBarChart3D(boolean barChart3D) {
		this.barChart3D = barChart3D;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
