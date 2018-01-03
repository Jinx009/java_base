package main.entry.webapp.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@RequestMapping(value = "/front/p")
@Controller
public class FrontPageController extends BaseController{

	@RequestMapping(path = "/login")
	public String login(){
		return "/front/login";
	}
	
}
