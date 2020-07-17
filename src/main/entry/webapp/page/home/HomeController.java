package main.entry.webapp.page.home;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/home")
public class HomeController {
	

	
	/**
	 * 错误页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/error")
	public String error(HttpServletRequest request){
		return "/home/error";
	}
	
	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		return "/page/index";
	}
	
	@RequestMapping(path = "/device")
	public String device(HttpServletRequest request){
		return "/page/device";
	}
	
	@RequestMapping(path = "/log")
	public String log(HttpServletRequest request){
		return "/page/log";
	}
	
	@RequestMapping(path = "/map")
	public String map(HttpServletRequest request){
		return "/page/map";
	}
	
	@RequestMapping(path = "/result")
	public String result(HttpServletRequest request){
		return "/page/result";
	}
	
	@RequestMapping(path = "/tcp")
	public String tcp(HttpServletRequest request){
		return "/page/tcp";
	}
	
	@RequestMapping(path = "/list")
	public String list(HttpServletRequest request){
		return "/page/list";
	}
	
	
}
