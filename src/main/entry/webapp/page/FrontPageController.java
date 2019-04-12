package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
public class FrontPageController extends BaseController{

	@RequestMapping(value = "/f/login")
	public String login(){
		return "/front/login";
	}
	
	@RequestMapping(value = "/f/register")
	public String register(){
		return "/front/register";
	}
	
	@RequestMapping(value = "/f/base")
	public String base(){
		return "/front/base";
	}
	
	
	@RequestMapping(value = "/f/p/index")
	public String index(){
		return "/front/index";
	}
	
	@RequestMapping(value = "/f/p/select")
	public String select(HttpServletRequest req,String type,String dateStr){
		req.setAttribute("dateStr", dateStr);
		req.setAttribute("type", type);
		return "/front/select";
	}
	
	
	@RequestMapping(value = "/f/p/pro_order")
	public String pro_order(){
		return "/front/pro_order";
	}
	
	@RequestMapping(value = "/f/p/pro_class_room")
	public String pro_class_room(){
		return "/front/pro_class_room";
	}
	
	@RequestMapping(value = "/f/p/me")
	public String me(){
		return "/front/me";
	}
	
	@RequestMapping(value = "/f/p/pro_swimming_pool")
	public String pro_swimming_pool(){
		return "/front/pro_swimming_pool";
	}
	
	@RequestMapping(value = "/f/p/pro_diving")
	public String diving(){
		return "/front/pro_diving";
	}
	
	
}
