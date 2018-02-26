package main.entry.webapp.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/wx/p")
public class WeixinParkPageController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(WeixinParkPageController.class);
	
	@RequestMapping(path = "/detail")
	public String parkDetail(String app){
		log.warn("");
		return "";
	}
	
}
