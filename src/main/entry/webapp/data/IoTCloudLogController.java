package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/iot/log")
public class IoTCloudLogController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(IoTCloudLogController.class);
	
	@Autowired
	private IotCloudLogService iotCloudLogService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p,Integer type,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudLogService.pageList(p,type,mac));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
