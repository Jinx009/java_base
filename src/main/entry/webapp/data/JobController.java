package main.entry.webapp.data;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.IoTCloudDevice;
import main.entry.webapp.BaseController;
import service.IoTCloudDeviceService;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/job")
public class JobController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private IoTCloudDeviceService ioTCloudDeviceService;
	
	@RequestMapping(path = "/send")
	@ResponseBody
	public Resp<?> job(@RequestBody Map<String,Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String mac = getString(data, "mac");
			String data1 = getString(data, "data");
			IoTCloudDevice ioTCloudDevice = ioTCloudDeviceService.findByMac(mac);
			if(ioTCloudDevice.getType()==1){
				String msg = HttpUtils.putUnicomJson(UnicomNoticeController.getSendUrl(ioTCloudDevice.getImei()), "{\"resourceValue\":\""+data1+"\"}\"","emhhbndheTpaaGFud2F5ITIz");
				log.warn("msg:{}",msg);
			}else if(ioTCloudDevice.getType()==2){
				
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
