package main.entry.webapp.mqtt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/mqtt/manager")
public class MqttDataController extends BaseController{

	@RequestMapping(path = "/start")
	@ResponseBody
	public String start(){
		new Thread(){
			public void run(){
				new DataManager().start();
			}
		}.start();
		return "ok";
	}
	
}
