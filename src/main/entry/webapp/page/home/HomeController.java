package main.entry.webapp.page.home;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	/**
	 * 登录页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		return "/home/index";
	}
	
	@RequestMapping(path = "/unicom/device")
	public String unicom_device(HttpServletRequest request){
		return "/home/unicom_device";
	}
	
	@RequestMapping(path = "/mobile/device")
	public String mobile_device(HttpServletRequest request){
		return "/home/mobile_device";
	}
	
	@RequestMapping(path = "/telcom/device")
	public String telcom_device(HttpServletRequest request){
		return "/home/telcom_device";
	}
	
	@RequestMapping(path = "/lora/device")
	public String lora_device(HttpServletRequest request){
		return "/home/lora_device";
	}
	
	@RequestMapping(path = "/up/log")
	public String telcom_log(HttpServletRequest request){
		return "/home/up_log";
	}
	
	@RequestMapping(path = "/down/log")
	public String unicom_log(HttpServletRequest request){
		return "/home/down_log";
	}
	
	/**
	 * 错误页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/error")
	public String error(HttpServletRequest request){
		return "/home/error";
	}
}
