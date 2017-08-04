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
	
	/**
	 * 示意图
	 * @return
	 */
	@RequestMapping(path = "/view")
	public String view(){
		return "/home/pro/view";
	}
	
	/**
	 * pos机列表
	 * @return
	 */
	@RequestMapping(path = "/pos")
	public String pos(){
		return "/home/pro/pos";
	}
	
	/**
	 * pos机订单列表
	 * @return
	 */
	@RequestMapping(path = "/order")
	public String order(){
		return "/home/pro/order";
	}
	
	/**
	 * pos机账户列表
	 * @return
	 */
	@RequestMapping(path = "/account")
	public String account(){
		return "/home/pro/account";
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
	 * 数据图表页面
	 * @return
	 */
	@RequestMapping(path = "/data")
	public String data(){
		return "/home/pro/data";
	}
	
}
