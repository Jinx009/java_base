package main.entry.webapp;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.log.LogSensorLogService;

@Controller
@RequestMapping(value = "/common/log")
public class LogsController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LogsController.class);
	
	@Autowired
	private LogSensorLogService logSensorDeviceService;
	
	@RequestMapping(path = "/deviceAlive",produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String deviceAlive(){
		try {
			String res = "<html><body>";
			List<String> list = logSensorDeviceService.findAlive();
			if(list!=null&&!list.isEmpty()){
				res += "<h2>当日激活地磁个数："+list.size()+" 个</h2>";
				for(String  s :list){
					res+= "<p style=\"font-size:16px;\">MAC："+s+"；时间："+logSensorDeviceService.findDate(s)+"</p>";
				}
			}
			return res+"</body></html>";
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	

	
}