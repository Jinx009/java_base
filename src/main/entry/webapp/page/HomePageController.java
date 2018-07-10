package main.entry.webapp.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController extends BaseController{
	
	@RequestMapping(path = "/pro_driver")
	public String driver(){
		return "/home/pro_driver";
	}
	
	@RequestMapping(path = "/pro_driver_new")
	public String driver_new(){
		return "/home/pro_driver_new";
	}
	
	@RequestMapping(path = "/pro_task_new")
	public String task_new(){
		return "/home/pro_task_new";
	}
	
	@RequestMapping(path = "/pro_task")
	public String task(){
		return "/home/pro_task";
	}
	
	
}
