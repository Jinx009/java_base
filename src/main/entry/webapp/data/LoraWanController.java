package main.entry.webapp.data;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/loraWan")
public class LoraWanController extends BaseController{
	
	@Autowired
	private IotCloudLogService iotCloudLogService;
	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	
	private static final Logger log = LoggerFactory.getLogger(LoraWanController.class);
	

	@RequestMapping(path = "/push")
	@ResponseBody
	public Resp<?> push(@RequestBody String r){
		Resp<?> resp = new Resp<>(false);
		log.warn("msg:{}",r);
		try {
			JSONObject jsonObject = JSONObject.parseObject(r);
			IotCloudLog iotCloudLog = new IotCloudLog();
			IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByMac(JSONObject.parseObject(jsonObject.getString("DevEUI_uplink")).getString("DevEUI"));
			iotCloudLog.setData(JSONObject.parseObject(jsonObject.getString("DevEUI_uplink")).getString("DevEUI")+JSONObject.parseObject(jsonObject.getString("DevEUI_uplink")).getString("payload_hex"));
			iotCloudLog.setFromSite("lorawan");
			iotCloudLog.setCreateTime(new Date());
			iotCloudLog.setImei(ioTCloudDevice.getImei());
			iotCloudLog.setType(0);
			iotCloudLog.setMac(ioTCloudDevice.getMac());
			iotCloudLogService.save(iotCloudLog);
			send(JSONObject.parseObject(jsonObject.getString("DevEUI_uplink")).getString("DevEUI")+JSONObject.parseObject(jsonObject.getString("DevEUI_uplink")).getString("payload_hex"),ioTCloudDevice.getUdpIp(),ioTCloudDevice.getUdpPort());
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
