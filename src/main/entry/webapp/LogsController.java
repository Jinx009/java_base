package main.entry.webapp;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.log.LogSensorHeartService;

@Controller
@RequestMapping(value = "/common/log")
public class LogsController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LogsController.class);
	
	@Autowired
	private LogSensorHeartService logSensorDeviceService;
	
	@RequestMapping(path = "/deviceAlive",produces = {"text/html"})
	@ResponseBody
	public String deviceAlive(){
		try {
			String res = "<html><body>";
			List<String> list = logSensorDeviceService.findAlive();
			if(list!=null&&!list.isEmpty()){
				res += "<h1>today alive total："+list.size()+" sensors</h1>";
				for(String  s :list){
					res+= "<p style=\"font-size:16px;\">"+s+"</p>";
				}
			}
			return res+"</body></html>";
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	

	
}