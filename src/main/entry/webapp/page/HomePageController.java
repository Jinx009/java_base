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

	@RequestMapping(path="/pro_switch")
	public String pro_switch(){
		return "/home/pro_switch";
	}
	
	@RequestMapping(path="/pro_diving2")
	public String pro_diving2(){
		return "/home/pro_diving2";
	}
	
	@RequestMapping(path="/pro_photo")
	public String pro_photo(){
		return "/home/pro_photo";
	}
	
	@RequestMapping(path="/pro_swim_switch")
	public String pro_swim_switch(){
		return "/home/pro_swim_switch";
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
