package main.entry.webapp.page.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController {

	/**
	 * 数据图表
	 * @return
	 */
	@RequestMapping(path = "/lot")
	public String lot(){
		return "/home/pro/lot";
	}
	
	/**
	 * 地理位置
	 * @return
	 */
	@RequestMapping(path = "/location")
	public String location(){
		return "/home/pro/location";
	}
	
	/**
	 * 设备信息
	 * @return
	 */
	@RequestMapping(path = "/device")
	public String device(){
		return "/home/pro/device";
	}
	
	/**
	 * 停车数统计
	 * @return
	 */
	@RequestMapping(path = "/car")
	public String car(){
		return "/home/pro/car";
	}
	
	/**
	 * 蓝牙设备
	 * @return
	 */
	@RequestMapping(path = "/blue")
	public String blue(){
		return "/home/pro/blue";
	}
	
	/**
	 * 蓝牙设备
	 * @return
	 */
	@RequestMapping(path = "/bikes")
	public String bikes(){
		return "/home/pro/bikes";
	}
	
}
