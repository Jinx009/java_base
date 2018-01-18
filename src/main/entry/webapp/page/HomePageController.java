package main.entry.webapp.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController extends BaseController{
	
	/**
	 * 车检器管理
	 * @return
	 */
	@RequestMapping(path = "/device/sensor")
	public String sensor(){
		return "/home/device/sensor";
	}
	
	/**
	 * 车检器管理
	 * @return
	 */
	@RequestMapping(path = "/device/pos")
	public String pos(){
		return "/home/device/pos";
	}
	
	@RequestMapping(path = "/device/error")
	public String error(){
		return "/home/device/error";
	}
	
	@RequestMapping(path = "/parking_data/time")
	public String time(){
		return "/home/parking_data/time";
	}
	
	@RequestMapping(path = "/parking_data/inOut")
	public String inOut(){
		return "/home/parking_data/inOut";
	}
	
	@RequestMapping(path = "/parking_data/rush")
	public String rush(){
		return "/home/parking_data/rush";
	}
	
	@RequestMapping(path = "/order/list")
	public String order(){
		return "/home/order/list";
	}
	
	@RequestMapping(path = "/order/rule_add")
	public String role_add(){
		return "/home/order/rule_add";
	}
	
	@RequestMapping(path = "/order/detail")
	public String detail(){
		return "/home/order/detail";
	}
	
	@RequestMapping(path = "/order/rule")
	public String rule(){
		return "/home/order/rule";
	}
	
	@RequestMapping(path = "/setting/pos")
	public String setting(){
		return "/home/setting/pos";
	}
	
	@RequestMapping(path = "/setting/pos_add")
	public String pos_add(){
		return "/home/setting/pos_add";
	}
	
	
	@RequestMapping(path = "/setting/location")
	public String location(){
		return "/home/setting/location";
	}
	
	@RequestMapping(path = "/setting/location_update")
	public String location_update(){
		return "/home/setting/location_update";
	}
	
	@RequestMapping(path = "/setting/area")
	public String area(){
		return "/home/setting/area";
	}
	
	@RequestMapping(path = "/setting/area_update")
	public String area_update(){
		return "/home/setting/area_update";
	}
	
	@RequestMapping(path = "/user/sign")
	public String user_sign(){
		return "/home/user/sign";
	}
	

}
