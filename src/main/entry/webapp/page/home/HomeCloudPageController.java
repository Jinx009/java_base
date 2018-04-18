package main.entry.webapp.page.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@RequestMapping(value = "/home/c")
@Controller
public class HomeCloudPageController extends BaseController{

	
	@RequestMapping(path = "/accessControl")
	public String accessControl(){
		return "/cloud/accessControl";
	}
	
	@RequestMapping(path = "/community")
	public String community(){
		return "/cloud/community";
	}
	
	@RequestMapping(path = "/fireData")
	public String fireData(){
		return "/cloud/fireData";
	}
	
	@RequestMapping(path = "/fireList")
	public String fireList(){
		return "/cloud/fireList";
	}
	
}
