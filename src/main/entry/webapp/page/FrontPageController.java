package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import database.models.active.Active;
import main.entry.webapp.data.HomeDataController;
import service.basicFunctions.active.ActiveService;

@Controller()
@RequestMapping(value = "/front/p")
public class FrontPageController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeDataController.class);
	
	@Autowired
	private ActiveService activeService;

	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		String key = request.getParameter("_key");
		Active active = activeService.getByKey(key);
		logger.warn(JSON.toJSONString(active));
		if(active!=null){
			request.setAttribute("_id",active.getId());
		}else{
			request.setAttribute("_id",0);
		}
		
		return "/index";
	}
	
}
