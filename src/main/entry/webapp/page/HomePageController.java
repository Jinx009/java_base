package main.entry.webapp.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController extends BaseController{
	
	@RequestMapping(path = "/pro_user")
	public String pro_user(){
		return "/home/pro_user";
	}

	@RequestMapping(path = "/pro_class")
	public String pro_class(){
		return "/home/pro_class";
	}
	
	@RequestMapping(path = "/pro_class_order")
	public String pro_class_order(){
		return "/home/pro_class_order";
	}
	
	@RequestMapping(path = "/pro_order")
	public String pro_order(){
		return "/home/pro_order";
	}
	
}
