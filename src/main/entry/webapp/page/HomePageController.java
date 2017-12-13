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
	
	@RequestMapping(path = "/account/pos")
	public String account(){
		return "/home/account/pos";
	}
	

}
