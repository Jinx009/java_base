package main.entry.webapp.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController extends BaseController{
	
	@RequestMapping(path = "/pro_user")
	public String driver(){
		return "/home/pro_user";
	}
	
	@RequestMapping(path = "/pro_vedio")
	public String driver_new(){
		return "/home/pro_vedio";
	}
	
	@RequestMapping(path = "/pro_vedio_new")
	public String task_new(){
		return "/home/pro_vedio_new";
	}
	
	
}
