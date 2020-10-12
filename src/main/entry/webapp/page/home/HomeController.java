package main.entry.webapp.page.home;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	/**
	 * 登录页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		return "/home/index";
	}
	
	@RequestMapping(path = "/qj/cmd")
	public String qj_cmd(HttpServletRequest request){
		return "/home/qj_cmd";
	}
	
	@RequestMapping(path = "/unicom/device")
	public String unicom_device(HttpServletRequest request){
		return "/home/unicom_device";
	}
	
	@RequestMapping(path = "/mobile/device")
	public String mobile_device(HttpServletRequest request){
		return "/home/mobile_device";
	}
	
	@RequestMapping(path = "/telcom/device")
	public String telcom_device(HttpServletRequest request){
		return "/home/telcom_device";
	}
	
	@RequestMapping(path = "/lora/device")
	public String lora_device(HttpServletRequest request){
		return "/home/lora_device";
	}
	
	@RequestMapping(path = "/up/log")
	public String telcom_log(HttpServletRequest request){
		return "/home/up_log";
	}
	
	@RequestMapping(path = "/down/log")
	public String unicom_log(HttpServletRequest request){
		return "/home/down_log";
	}
	
	@RequestMapping(path = "/qj/wh")
	public String wuhan(HttpServletRequest request){
		return "/home/wuhan";
	}
	
	@RequestMapping(path = "/qj/lost")
	public String lost(HttpServletRequest request){
		return "/home/lost";
	}
	
	
	@RequestMapping(path = "/add/telcom")
	public String add_telcom(HttpServletRequest request){
		return "/home/add_telcom";
	}
	
	
	@RequestMapping(path = "/device/map")
	public String device_map(HttpServletRequest request){
		return "/home/map";
	}
	
	@RequestMapping(path = "/device/detail")
	public String device_detail(HttpServletRequest request){
		return "/home/detail";
	}
	
	@RequestMapping(path = "/device/lost_list")
	public String device_lost(HttpServletRequest request){
		return "/home/lost_list";
	}
	
	@RequestMapping(path = "/device/angle")
	public String device_angle(HttpServletRequest request){
		return "/home/angle";
	}
	
	@RequestMapping(path = "/device/more")
	public String device_more(HttpServletRequest request){
		return "/home/more";
	}
	
	@RequestMapping(path = "/event/log")
	public String event_log(HttpServletRequest request,String mac){
		request.setAttribute("mac", mac); 
		return "/home/event";
	}
	
	
	/**
	 * 错误页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/error")
	public String error(HttpServletRequest request){
		return "/home/error";
	}
}
