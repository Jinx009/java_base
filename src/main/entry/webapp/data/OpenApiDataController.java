package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;

@Controller
public class OpenApiDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(OpenApiDataController.class);
	

	@RequestMapping(value = "/mofang/session")
	@ResponseBody
	public String getSession(){
		try {
			String sessionId = getMofangSessionId();
			return sessionId;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
}
