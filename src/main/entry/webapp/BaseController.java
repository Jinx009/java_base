package main.entry.webapp;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import utils.IPUtil;

@Controller
public class BaseController {
	
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public String getClientIp(HttpServletRequest request){
		return IPUtil.getRemortIP(request);
	}
	
}
