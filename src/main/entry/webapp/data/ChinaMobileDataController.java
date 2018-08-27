package main.entry.webapp.data;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import database.models.vo.ChinaMobilePushDataModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;

@Controller
@RequestMapping(value = "/china_mobile")
public class ChinaMobileDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ChinaMobileDataController.class);
	
	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService IotCloudLogService;
	
	/**
	 * 中国移动数据推送
	 * @param data
	 * @return
	 */
	@RequestMapping(path = "/notice/push")
	@ResponseBody
	public String push(@RequestBody String data){//msg=xxx&nonce=xxx&signature=xxx 
		try {
			//log.warn("msg:{},nonce:{},signature:{}",msg,nonce,signature);
			JSONObject jsonObject = JSONObject.parseObject(data);
			if(isJsonArray(data)){
				JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("msg"));
				JSONObject jsonObject2 = jsonArray.getJSONObject(0);
				if(1==jsonObject2.getIntValue("type")){
					List<ChinaMobilePushDataModel> list = JSONArray.parseArray(jsonObject.getString("msg"), ChinaMobilePushDataModel.class);
					for(ChinaMobilePushDataModel model:list){
						IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByDeviceId(model.getDev_id());
						if(ioTCloudDevice!=null){
							IotCloudLog iotCloudLog = new IotCloudLog();
							iotCloudLog.setCreateTime(new Date());
							iotCloudLog.setData(model.getValue());
							iotCloudLog.setFromSite("chian_mobile");
							iotCloudLog.setImei(ioTCloudDevice.getImei());
							iotCloudLog.setMac(ioTCloudDevice.getMac());
							iotCloudLog.setType(4);
							IotCloudLogService.save(iotCloudLog);
							send(iotCloudLog.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
						}
					}
				}else{
					ChinaMobilePushDataModel chinaMobilePushDataModel = JSONObject.parseObject(jsonObject.getString("msg"), ChinaMobilePushDataModel.class);
					IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByDeviceId(chinaMobilePushDataModel.getDev_id());
					IotCloudLog iotCloudLog = new IotCloudLog();
					iotCloudLog.setCreateTime(new Date());
					iotCloudLog.setData(chinaMobilePushDataModel.getValue());
					iotCloudLog.setFromSite("chian_mobile");
					iotCloudLog.setImei(ioTCloudDevice.getImei());
					iotCloudLog.setMac(ioTCloudDevice.getMac());
					iotCloudLog.setType(4);
					IotCloudLogService.save(iotCloudLog);
					send(iotCloudLog.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
				}
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return "success";
	}
	
	private static boolean isJsonArray(String content){
		try {
			JSONArray.parseArray(content);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
