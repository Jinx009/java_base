package main.entry.webapp.page.pro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/p/pro/")
public class ProHomePageController extends BaseController{

	public String login(){
		return "/pro/home/login";
	}
	
}
