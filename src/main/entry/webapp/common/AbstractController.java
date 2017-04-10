package main.entry.webapp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.common.AbstractService;

@Controller
@RequestMapping(value = "/common")
public class AbstractController {

	@Autowired
	private AbstractService abstractService;
	
	@RequestMapping(path = "/abstract")
	@ResponseBody
	public String abstractMethod(){
		return abstractService.getStr();
	}
	
}
