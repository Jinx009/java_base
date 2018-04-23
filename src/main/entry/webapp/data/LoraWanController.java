package main.entry.webapp.data;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import database.models.IotCloudLog;
import database.models.vo.lorawan.LoraWanBaseModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/loraWan")
public class LoraWanController extends BaseController{
	
	@Autowired
	private IotCloudLogService iotCloudLogService;
	
	private static final Logger log = LoggerFactory.getLogger(LoraWanController.class);
	

	public Resp<?> push(@RequestBody String r){
		Resp<?> resp = new Resp<>(false);
		try {
			LoraWanBaseModel loraWanBaseModel = JSONObject.parseObject(r,LoraWanBaseModel.class);
			if(loraWanBaseModel!=null){
					IotCloudLog iotCloudLog = new IotCloudLog();
					iotCloudLog.setData(loraWanBaseModel.getDevEUI_uplink().getCustomerData().getAlr().getPro());
					iotCloudLog.setFromSite("lorawan");
					iotCloudLog.setCreateTime(new Date());
					iotCloudLog.setImei(loraWanBaseModel.getDevEUI_uplink().getDevEUI());
					iotCloudLog.setType(0);
					iotCloudLog.setMac(loraWanBaseModel.getDevEUI_uplink().getDevEUI());
					iotCloudLogService.save(iotCloudLog);
					send(loraWanBaseModel.getDevEUI_uplink().getCustomerData().getAlr().getPro());
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
