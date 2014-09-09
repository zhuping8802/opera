package org.ping.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.general.DefaultPieDataset;

public class PieServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2187774872836623792L;

	/**
	 * Constructor of the object.
	 */
	public PieServlet() {
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
		//设置饼图数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("黑心矿难", 720);
		dataset.setValue("醉酒驾驶", 530);
		dataset.setValue("城管强拆", 210);
		dataset.setValue("医疗事故", 91);
		dataset.setValue("其他", 66);
		ChartPie pie = new ChartPie(dataset);
//		pie.setBarChart3D(true);
		ChartUtil.exportChartToPage(request, response, pie);
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
