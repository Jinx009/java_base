package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
public class FrontPageController extends BaseController{

	@RequestMapping(value = "/f/p/login")
	public String login(){
		return "/front/login";
	}
	
	@RequestMapping(value = "/front/p/detail")
	public String detail(HttpServletRequest req,Integer id){
		req.setAttribute("_id", id);
		return "/front/detail";
	}
	
	
	@RequestMapping(value = "/front/p/wait")
	public String wait1(){
		return "/front/wait";
	}
	
	@RequestMapping(value = "/front/p/done")
	public String done(){
		return "/front/done";
	}

}
