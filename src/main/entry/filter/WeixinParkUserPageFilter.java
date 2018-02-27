package main.entry.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.models.WeixinParkUser;
import utils.BaseConstant;

public class WeixinParkUserPageFilter implements Filter {
	
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = false;
	protected String forwardPath = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}
	/**
	 * 未得到授权的链接重定向到登录页
	 */
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		// 通过检查session中的变量，过虑请求
		WeixinParkUser weixinParkUser = (WeixinParkUser) session.getAttribute(BaseConstant.WEB_USER);
		if(weixinParkUser==null){
			httpServletResponse.sendRedirect("/wx/p/me");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		this.forwardPath = filterConfig.getInitParameter("forwardpath");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}

}
