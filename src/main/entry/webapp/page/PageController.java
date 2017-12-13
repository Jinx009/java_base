package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "")
	public String base(){
		return "/home/index";
	}
	
	/**
	 * spring报错
	 * @return
	 */
	@RequestMapping(value = "DevMgmt/DiscoveryTree.xml")
	public String DevMgmt(){
		return "/DevMgmt/DiscoveryTree";
	}

	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(){
		return "/home/index";
	}
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/home/index")
	public String homeIndex(){
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
	
	/**
	 * 跳转至测试页
	 * @return
	 */
	@RequestMapping(value = "/404")
	public String test(){
		return "/404";
	}
	

}
