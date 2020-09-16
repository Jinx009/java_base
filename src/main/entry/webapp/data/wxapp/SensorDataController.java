package main.entry.webapp.data.wxapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/sensor")
public class SensorDataController extends BaseController{

	private Logger log = LoggerFactory.getLogger(SensorDataController.class);
	
	public Resp<?> save(){
		Resp<?> resp = new Resp<>(false);
		try {
			
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
