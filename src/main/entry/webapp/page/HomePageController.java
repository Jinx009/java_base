package main.entry.webapp.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController extends BaseController{
	
	@RequestMapping(path = "/pro_goods")
	public String driver(){
		return "/home/pro_goods";
	}
	
	@RequestMapping(path = "/pro_price")
	public String driver_new(){
		return "/home/pro_price";
	}
	
	@RequestMapping(path = "/pro_vedio_new")
	public String task_new(){
		return "/home/pro_vedio_new";
	}
	
	
}
