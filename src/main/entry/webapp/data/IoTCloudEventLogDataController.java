package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.IoTCloudEventLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.IoTCloudEventLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/event")
public class IoTCloudEventLogDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(IoTCloudEventLogDataController.class);
	
	@Autowired
	private IoTCloudEventLogService ioTCloudEventLogService;
	
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String mac,String fatherType,String type,int p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<IoTCloudEventLog> list = ioTCloudEventLogService.findByPage(mac,fatherType,type,p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
