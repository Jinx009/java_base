package main.entry.webapp;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import utils.StringUtil;
import utils.ip.IPUtil;

@Controller
public class BaseController {

	
	public String getString(Map<String,Object> data, String key){
		String value = String.valueOf(data.get(key));
		if(StringUtil.isBlank(value)){
			return "";
		}
		return value;
	}
	
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public String getClientIp(HttpServletRequest request){
		return IPUtil.getRemortIP(request);
	}
	
}
