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
	
	@RequestMapping(value = "/f/p/pro_order")
	public String pro_order(){
		return "/front/pro_order";
	}
	
	@RequestMapping(value = "/f/p/pro_class")
	public String pro_class(){
		return "/front/pro_class";
	}
	
	@RequestMapping(value = "/f/p/pro_swimming_pool")
	public String pro_swimming_pool(){
		return "/front/pro_swimming_pool";
	}
	
	@RequestMapping(value = "/f/p/pro_class_room")
	public String pro_class_room(){
		return "/front/pro_class_room";
	}
	
	
}
