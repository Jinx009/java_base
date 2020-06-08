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
	
	@RequestMapping(path = "/pro_order")
	public String pro_order(){
		return "/home/pro_order";
	}
	
	@RequestMapping(path = "/pro_order_new")
	public String task_new(){
		return "/home/pro_order_new";
	}
	@RequestMapping(path = "/pro_order_m")
	public String m(){
		return "/home/pro_order_m";
	}
	
	@RequestMapping(path = "/pro_order_add")
	public String task_add(){
		return "/home/pro_order_add";
	}
	
	@RequestMapping(path = "/pro_order_add_list")
	public String task_add_list(){
		return "/home/pro_order_add_list";
	}
	
}
