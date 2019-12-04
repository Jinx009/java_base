package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.device.DeviceSensor;
import database.models.log.LogSensorHeart;
import database.models.log.LogSensorStatus;

public class WuhanSendUtils {

	private static String appKey = "";
	private static String userKey = "";
	private static String URL  = "http://test.wuhanparking.com";
	
//	[{
//		"log_id": "169033009725440",
//		"berthcode": "210235",
//		"changetime": "2015-05-01 12: 01: 02",
//		"berthstatus": 0,
//		"electricity": “0.00”,
//		"voltage": 10,
//		”regionid”: ”002002”
//	}]
	public static String sendStatus(LogSensorStatus log,DeviceSensor sensor){
		try {
			long time = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp = String.valueOf(time);
			String method = "uploadberthstatus";
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("log_id", log.getId());
			data.put("berthcode", sensor.getDesc());
			data.put("changetime", sdf.format(log.getChangeTime()));
			data.put("berthstatus", sensor.getAvailable());
			data.put("regionid", sensor.getCameraName());
			data.put("electricity", Double.valueOf(sensor.getBatteryVoltage())/3.60);
			data.put("voltage", sensor.getBatteryVoltage());
			JSONArray arr = new JSONArray();
			arr.add(data);
			map.put("pushdata", arr);
			String signStr = "appkey="+appKey+"&method="+method+"&timestamp="+timestamp+""+userKey;
			String params = "appkey="+appKey+"&timestamp="+timestamp+"&method="+method+"&sign="+MD5Util.toMD5(signStr)+"&pushdata="+JSONObject.toJSONString(map).replaceAll("\\\\","");
			String res = HttpUtil.sendPost(URL, params);
			String status = JSONObject.parseObject(res).getString("status");
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	public static String sendHeart(LogSensorHeart log,DeviceSensor sensor){
		try {
			long time = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp = String.valueOf(time);
			String method = "pushheartbeat";
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("EquipmentType",1);
			data.put("EquipmentCode", sensor.getMac());
			data.put("PushTime", sdf.format(log.getCreateTime()));
			data.put("EquipmentStatus", 1);
			data.put("regionid", sensor.getCameraName());
			data.put("electricity", Double.valueOf(sensor.getBatteryVoltage())/3.60);
			data.put("voltage", sensor.getBatteryVoltage());
			JSONArray arr = new JSONArray();
			arr.add(data);
			map.put("pushdata", arr);
			String signStr = "appkey="+appKey+"&method="+method+"&timestamp="+timestamp+""+userKey;
			String params = "appkey="+appKey+"&timestamp="+timestamp+"&method="+method+"&sign="+MD5Util.toMD5(signStr)+"&pushdata="+JSONObject.toJSONString(map).replaceAll("\\\\","");
			String res = HttpUtil.sendPost(URL, params);
			String status = JSONObject.parseObject(res).getString("status");
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	
	}
	
}
