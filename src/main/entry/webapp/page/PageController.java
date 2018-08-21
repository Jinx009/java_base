package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
public class PageController extends BaseController{
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/p/index")
	public String login(){
		return "/home/index";
	}
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/p/register")
	public String register(){
		return "/home/register";
	}
	
	@RequestMapping(value = "/p/paper_new")
	public String paper_new(HttpServletRequest req){
		if(getSessionHomeUser(req)==null){
			return "/home/index";
		}
		return "/home/paper_new";
	}
	
	@RequestMapping(value = "/p/paper")
	public String paper(HttpServletRequest req){
		if(getSessionHomeUser(req)==null){
			return "/home/index";
		}
		return "/home/paper";
	}
	
	@RequestMapping(value = "/p/result")
	public String result(HttpServletRequest req){
		if(getSessionHomeUser(req)==null){
			return "/home/index";
		}
		return "/home/result";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
