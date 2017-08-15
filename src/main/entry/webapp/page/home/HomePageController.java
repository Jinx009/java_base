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
	
	/**
	 * 页面授权
	 * @return
	 */
	@RequestMapping(path = "author_page")
	public String authorPage(){
		return "/home/menu/author_page";
	}
	
	/**
	 * 数据授权
	 * @return
	 */
	@RequestMapping(path = "author_data")
	public String authorData(){
		return "/home/menu/author_data";
	}
	
	/**
	 * 前端用户列表
	 * @return
	 */
	@RequestMapping(value = "web_user")
	public String webUser(){
		return "/home/user/list";
	}
	
	@RequestMapping(value = "/active/add")
	public String add(){
		return "/active/add";
	}
	
	
	@RequestMapping(value = "/active/list")
	public String list(){
		return "/active/list";
	}
	
	@RequestMapping(value = "/active/user")
	public String activeUser(){
		return "/active/user";
	}
}
