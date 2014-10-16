package org.ping.core.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求过滤器，提供实例化WebContext功能实现
 */
public class WebContextFilter implements Filter {

    /**
     * Default constructor. 
     */
    public WebContextFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;  
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		ServletContext servletContext = httpRequest.getSession()
				.getServletContext();
		WebContext.create(httpRequest, httpResponse, servletContext);
		chain.doFilter(request, response);
		WebContext.clear();  
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
}
