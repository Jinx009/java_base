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

	@RequestMapping(path = "/pro_swimming")
	public String pro_swimming(){
		return "/home/pro_swimming";
	}
	
	@RequestMapping(path = "/pro_diving")
	public String pro_diving(){
		return "/home/pro_diving";
	}
	
	@RequestMapping(path = "/pro_class_room")
	public String pro_class_room(){
		return "/home/pro_class_room";
	}
	
}
