package main.entry.webapp.page;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.basicFunctions.UserService;



@Controller
public class PageController {
	
	@Autowired
	private UserService userService;

	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(){
		return "/index";
	}
	

}
