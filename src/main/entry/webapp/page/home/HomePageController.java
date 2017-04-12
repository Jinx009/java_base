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
	@RequestMapping(path = "/changePwd")
	public String changePwd(){
		return "/home/account/changePwd";
	}
	
	/**
	 * 角色管理
	 * @return
	 */
	@RequestMapping(path = "/role")
	public String role(){
		return "/home/account/role";
	}
	
}
