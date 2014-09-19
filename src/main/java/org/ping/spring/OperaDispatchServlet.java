package org.ping.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * 拦截特殊请求实现ajaxbatch操作，其它请求直接放行
 * @author ping.zhu
 *
 */
public class OperaDispatchServlet extends DispatcherServlet{

	@Override
	protected void doService(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		// TODO Auto-generated method stub
		super.doService(arg0, arg1);
	}

}
