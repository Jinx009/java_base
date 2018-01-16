package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;
import utils.Resp;

@RequestMapping(value = "/home/d")
@Controller
public class ErrorFlowDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ErrorFlowDataController.class);
	
	public Resp<?> errors(){
		Resp<?> resp = new Resp<>(false);
		try {
			
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
