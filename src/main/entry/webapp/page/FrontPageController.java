package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import database.models.active.Active;
import service.basicFunctions.active.ActiveService;

@Controller()
@RequestMapping(value = "/front/p")
public class FrontPageController {
	
	@Autowired
	private ActiveService activeService;

	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		String key = request.getParameter("_key");
		Active active = activeService.getByKey(key);
		if(active!=null){
			request.setAttribute("_id",active.getId());
		}else{
			request.setAttribute("_id",0);
		}
		
		return "index";
	}
	
}
