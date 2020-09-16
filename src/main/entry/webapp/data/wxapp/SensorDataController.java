package main.entry.webapp.data.wxapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/sensor")
public class SensorDataController extends BaseController{

	private Logger log = LoggerFactory.getLogger(SensorDataController.class);
	
	@RequestMapping(value = "/mac")
	@ResponseBody
	public Resp<?> getMac(String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			String jsonStr = HttpUtils.get("http://iot-admin.zhanway.com/iot/iot/sensor/mac?mac="+mac);
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return new Resp<>(JSONObject.parseArray(obj.getString("data")));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(value = "/address")
	@ResponseBody
	public Resp<?> getAddress(String address,String location){
		Resp<?> resp = new Resp<>(false);
		try {
			String jsonStr = HttpUtils.get("http://iot-admin.zhanway.com/iot/iot/sensor/address?location="+location+"&address="+address);
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return new Resp<>(JSONObject.parseArray(obj.getString("data")));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Resp<?> save(String mac,String lng,String lat,String picUrl,String ver,String type,String address,String description,String location){
		Resp<?> resp = new Resp<>(false);
		try {
			String params = "mac="+mac+"&lng="+lng+"&lat="+lat+"&picUrl="+picUrl+"&ver="+ver+"&type="+type+"&address="+address+"&description="+
		description+"&location="+location;
			String jsonStr = HttpUtils.sendPost  ("http://iot-admin.zhanway.com/iot/iot/sensor/operation/save",params);
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return new Resp<>(obj);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
