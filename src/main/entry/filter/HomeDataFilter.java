package main.entry.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import utils.Resp;
import utils.RespData;
import utils.model.HomeConfigConstant;

public class HomeDataFilter implements Filter {
	
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = false;
	protected String forwardPath = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}
	/**
	 * 未得到授权的数据不能获取
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// 通过检查session中的变量，过虑请求
		HttpSession session = httpServletRequest.getSession();
		if(!HomeConfigConstant.checkSession(session.getId())){
			httpServletResponse.setContentType("application/json;charset=UTF-8");  
			PrintWriter out = httpServletResponse.getWriter();  
			out.print(JSON.toJSON(new Resp<>(RespData.CANT_IN_CODE,RespData.CANT_IN_MSG,null))); 
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
