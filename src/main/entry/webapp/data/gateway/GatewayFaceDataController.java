package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;
import utils.Resp;

@Controller
@RequestMapping(value = "/rest/face")
public class GatewayFaceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayFaceDataController.class);
	
	public Resp<?> face(){
		Resp<?> resp = new Resp<>(true);
		log.warn("data:{}",resp);
		return resp;
	}
	
}
