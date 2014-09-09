package org.ping.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.category.DefaultCategoryDataset;


public class BarServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public BarServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		ChartUtil.exportChartToPage(request, response, bar);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
