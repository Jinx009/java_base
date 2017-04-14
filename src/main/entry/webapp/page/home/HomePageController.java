package main.entry.webapp.page.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController {

	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(path = "/change_pwd")
	public String changePwd(){
		return "/home/account/change_pwd";
	}
	
	/**
	 * 角色管理
	 * @return
	 */
	@RequestMapping(path = "/role")
	public String role(){
		return "/home/account/role";
	}
	
	/**
	 * 页面管理
	 * @return
	 */
	@RequestMapping(path = "/page")
	public String page(){
		return "/home/menu/page";
	}
	
	/**
	 * 数据管理
	 * @return
	 */
	@RequestMapping(path = "/data")
	public String data(){
		return "/home/menu/data";
	}
	
	/**
	 * 用户管理
	 * @return
	 */
	@RequestMapping(path = "/user")
	public String user(){
		return "/home/account/user";
	}
	
}
