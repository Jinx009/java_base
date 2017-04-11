package main.entry.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import database.models.home.HomeResource;
import utils.model.HomeConfigConstant;

public class HomePageFilter implements Filter {
	
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
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String servletPath = httpServletRequest.getServletPath();
		HttpSession session = httpServletRequest.getSession();
		// 通过检查session中的变量，过虑请求
		if(!check(session, servletPath)){
			if(!HomeConfigConstant.checkSession(session.getId())){
				httpServletResponse.sendRedirect("/home/index");
			}else{
				httpServletResponse.sendRedirect("/home/error");
			}
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

	@SuppressWarnings("unchecked")
	private boolean check(HttpSession session,String uri) {
		List<HomeResource> list = (List<HomeResource>) HomeConfigConstant.getResourceBySession(session);
		if(null!=list&&!list.isEmpty()){
			for(HomeResource homeResource:list){
				if(uri.equals(homeResource.getUri())){
					return true;
				}
			}
		}
		return false;
	}

}
