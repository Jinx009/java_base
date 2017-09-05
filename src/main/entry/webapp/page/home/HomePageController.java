package main.entry.webapp.page.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController {
	
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
	
	/**
	 * 车位列表
	 * @return
	 */
	@RequestMapping(path = "/place")
	public String place(){
		return "/home/pro/place";
	}
	
	/**
	 * 更改插入新的车位
	 * @return
	 */
	@RequestMapping(path = "/insert")
	public String insert(){
		return "/home/pro/insert";
	}
	
	/**
	 * 区域管理
	 * @return
	 */
	@RequestMapping(path = "/area")
	public String area(){
		return "/home/pro/area";
	}
	
	/**
	 * 街道管理
	 * @return
	 */
	@RequestMapping(path = "/street")
	public String street(){
		return "/home/pro/street";
	}
	
	/**
	 * 故障管理
	 * @return
	 */
	@RequestMapping(path = "/error")
	public String error(){
		return "/home/pro/error";
	}
	
	/**
	 *全部订单
	 * @return
	 */
	@RequestMapping(path = "/allOrder")
	public String allOrder(){
		return "/home/pro/allOrder";
	}
	
	/**
	 *仪表盘
	 * @return
	 */
	@RequestMapping(path = "/table")
	public String table(){
		return "/home/pro/table";
	}
	
	/**
	 *占有率
	 * @return
	 */
	@RequestMapping(path = "/use")
	public String use(){
		return "/home/pro/use";
	}
	
	/**
	 *财务分析
	 * @return
	 */
	@RequestMapping(path = "/money")
	public String money(){
		return "/home/pro/money";
	}
	
	/**
	 *考勤管理
	 * @return
	 */
	@RequestMapping(path = "/job")
	public String job(){
		return "/home/pro/job";
	}
	
	/**
	 * 收费管理
	 * @return
	 */
	@RequestMapping(path = "/charge")
	public String charge(){
		return "/home/pro/charge";
	}
	
	/**
	 * 发票管理
	 * @return
	 */
	@RequestMapping(path = "/paper")
	public String paper(){
		return "/home/pro/paper";
	}
	
	/**
	 * 会员管理
	 * @return
	 */
	@RequestMapping(path = "/user")
	public String user(){
		return  "/home/pro/user";
	}
	
	/**
	 * 包月证管理
	 * @return
	 */
	@RequestMapping(path = "/month")
	public String month(){
		return  "/home/pro/month";
	}
	
	/**
	 * 推送管理
	 * @return
	 */
	@RequestMapping(path = "/send")
	public String send(){
		return "/home/pro/send";
	}
	
	/**
	 * 支付渠道管理
	 * @return
	 */
	@RequestMapping(path = "/pay")
	public String pay(){
		return "/home/pro/pay";
	}
	
	/**
	 * 用户增长率
	 * @return
	 */
	@RequestMapping(path = "/useradd")
	public String useradd(){
		return "/home/pro/useradd";
	}
	
	/**
	 * 新增收费规则
	 * @return
	 */
	@RequestMapping(value = "/chargeAdd")
	public String chargeAdd(){
		return "/home/pro/chargeAdd";
	}
}
