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
	
	

}
