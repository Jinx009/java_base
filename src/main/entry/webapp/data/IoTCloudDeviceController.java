package main.entry.webapp.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/iot/device")
public class IoTCloudDeviceController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(IoTCloudDeviceController.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.pageList(p,type));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/wuhan")
	@ResponseBody
	public Resp<?> wh(Integer p,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.getWuhan());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/localIp")
	@ResponseBody
	public Resp<?> type(String localIp){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.findByLocalIp(localIp) );
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
