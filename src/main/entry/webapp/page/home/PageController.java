package main.entry.webapp.page.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping(value = "404")
	public String page404(){
		return "/404";
	}
	
	@RequestMapping(value = "index")
	public String index(){
		return "/home/index";
	}
	
	@RequestMapping(value = "/")
	public String base(){
		return "/home/index";
	}
	
}
