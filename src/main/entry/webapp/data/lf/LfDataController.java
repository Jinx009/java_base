package main.entry.webapp.data.lf;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.lf.LfDevice;
import database.models.lf.LfLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.lf.LfDeviceService;
import service.basicFunctions.lf.LfLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/lfj")
public class LfDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(LfDataController.class);
	
	@Autowired
	private LfDeviceService lfDeviceService;
	@Autowired
	private LfLogService lfLogService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		return new Resp<>(lfDeviceService.findAll());
	}
	
	@RequestMapping(path = "dataPush")
	@ResponseBody
	public Resp<?> dataPush(@RequestBody String s){
		log.warn("data:{}",s);
		JSONArray arr = JSONObject.parseArray(s);
		for(int i =0;i<arr.size();i++) {
			JSONObject obj = arr.getJSONObject(i);
			String SensorID = obj.getString("SensorID");
			Integer SensorType = obj.getInteger("SensorType");
			String data1 = obj.getString("Data1");
			String data2 = obj.getString("Data2");
			String data3 = obj.getString("Data3");
			Long AcqTime = obj.getLong("AcqTime");
			Long PushTime = obj.getLong("PushTime");
			LfDevice lfDevice = lfDeviceService.findById(SensorID);
			if(lfDevice!=null){
				lfDevice.setAcqTime(AcqTime);
				lfDevice.setData1(data1);
				lfDevice.setData2(data2);
				lfDevice.setData3(data3);
				lfDevice.setPushTime(PushTime);
				lfDevice.setSensorType(SensorType);
				lfDeviceService.update(lfDevice);
			}else{
				lfDevice = new LfDevice();
				lfDevice.setAcqTime(AcqTime);
				lfDevice.setSensorType(SensorType);
				lfDevice.setData1(data1);
				lfDevice.setData2(data2);
				lfDevice.setData3(data3);
				lfDevice.setSensorID(SensorID);
				lfDevice.setPushTime(PushTime);
				lfDeviceService.save(lfDevice);
			}
			LfLog lfLog = new LfLog();
			lfLog.setAcqTime(AcqTime);
			lfLog.setSensorType(SensorType);
			lfLog.setData1(data1);
			lfLog.setData2(data2);
			lfLog.setData3(data3);
			lfLog.setSensorID(SensorID);
			lfLog.setPushTime(PushTime);
			lfLogService.save(lfLog);
		}
		return new Resp<>("");
	}
	
}
