package main.entry.webapp.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	
	/**
	 * spring报错
	 * @return
	 */
	@RequestMapping(value = "DevMgmt/DiscoveryTree.xml")
	public String DevMgmt(){
		return "/DevMgmt/DiscoveryTree";
	}

	

}
