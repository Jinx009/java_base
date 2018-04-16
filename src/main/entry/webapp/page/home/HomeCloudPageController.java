package main.entry.webapp.page.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@RequestMapping(value = "/home/c")
@Controller
public class HomeCloudPageController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(HomeCloudPageController.class);
	
	@RequestMapping(path = "/accessControl")
	public String accessControl(){
		return "/cloud/accessControl";
	}
	
}
