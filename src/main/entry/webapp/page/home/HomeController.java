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
