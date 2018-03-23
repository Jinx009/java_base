package main.entry.webapp.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
public class FrontPageController extends BaseController{

	@RequestMapping(value = "/f/p/login")
	public String login(){
		return "/front/login";
	}
	
	@RequestMapping(value = "/f/p/pro_site")
	public String pro_site(){
		return "/front/pro_site";
	}
	
}
