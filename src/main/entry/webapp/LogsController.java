package main.entry.webapp;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.device.DeviceSensor;
import database.models.device.DeviceSensorInfo;
import database.models.log.LogSensorHeart;
import main.entry.webapp.BaseController;
import service.basicFunctions.device.DeviceSensorInfoService;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.log.LogSensorLogService;
import utils.StringUtil;

@Controller
@RequestMapping(value = "/common/log")
public class LogsController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LogsController.class);
	
	@Autowired
	private LogSensorLogService logSensorDeviceService;
	@Autowired
	private DeviceSensorInfoService deviceSesnorInfoService;
	@Autowired
	private DeviceSensorService deviceSensorService;
	
	@RequestMapping(path = "/deviceAlive",produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String deviceAlive(){
		try {
			String res = "<html><body>";
			List<String> list = logSensorDeviceService.findAlive();
			if(list!=null&&!list.isEmpty()){
				res += "<h2>当日激活地磁个数："+list.size()+" 个</h2>";
				for(String  s :list){
					res+= "<p style=\"font-size:16px;\">MAC："+s+"；时间："+logSensorDeviceService.findDate(s)+"</p>";
				}
			}
			return res+"</body></html>";
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
	/**
	 * 查询心跳最新时间
	 * @return
	 */
	@RequestMapping(path = "logs")
	@ResponseBody
	public List<LogSensorHeart> logs(){
		List<LogSensorHeart> logs = new ArrayList<LogSensorHeart>();
		List<DeviceSensorInfo> list = deviceSesnorInfoService.find();
		for(DeviceSensorInfo deviceSesnorInfo : list) {
			LogSensorHeart logSensorHeart = logSensorDeviceService.findByInfoMac(deviceSesnorInfo.getMac());
			if(logSensorHeart!=null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logSensorHeart.setMac("mac:"+logSensorHeart.getMac());
				logSensorHeart.setBaseEnergy(deviceSesnorInfo.getParkNumber());
				logSensorHeart.setUid(deviceSesnorInfo.getAddress());
				logSensorHeart.setPanId(sdf.format(logSensorHeart.getCreateTime()));
				logs.add(logSensorHeart);
			}
		}
		return logs;
	}

	/**
	 * 查询区域间心跳个数
	 * @param date1
	 * @param time1
	 * @param date2
	 * @param time2
	 * @return
	 */
	@RequestMapping(path = "hearts")
	@ResponseBody
	public List<DeviceSensorInfo> list(String date1,String time1,String date2,String time2){
		List<DeviceSensorInfo> list = deviceSesnorInfoService.find();
		for(DeviceSensorInfo device : list) {
			if(StringUtil.isNotBlank(device.getMac())) {
				List<LogSensorHeart> list2 = logSensorDeviceService.findList(device.getMac(),date1,date2,time1,time2);
				if(list2!=null&&!list2.isEmpty()) {
					device.setMac("mac："+device.getMac());
					device.setId(list2.size());
				}else {
					device.setMac("mac："+device.getMac());
					device.setId(0);
				}
			}
			
		}
		return list;
	}
	
	@RequestMapping(path = "sensors")
	@ResponseBody
	public List<DeviceSensor> sensorsList(String mac,String address){
		List<DeviceSensorInfo> list = deviceSesnorInfoService.findByMacAndAddress(mac,address);
		List<DeviceSensor> sensors = new ArrayList<DeviceSensor>();
		for(DeviceSensorInfo device : list) {
			if(StringUtil.isNotBlank(device.getMac())) {
				DeviceSensor deviceSensor = deviceSensorService.findByMac(device.getMac());
				LogSensorHeart logSensorHeart = logSensorDeviceService.findByInfoMac(device.getMac());
				deviceSensor.setBaseEnergy(device.getParkNumber());
				if(logSensorHeart!=null) {
					deviceSensor.setCreateTime(logSensorHeart.getCreateTime());
					deviceSensor.setDesc(logSensorHeart.getRssi());
				}
				sensors.add(deviceSensor);
			}
			
			
		}
		return sensors;
	}
	
	@RequestMapping(path = "devices")
	@ResponseBody
	public List<DeviceSensor> devices(String mac,String address){
		List<DeviceSensorInfo> list = deviceSesnorInfoService.findByMacAndAddress(mac,address);
		List<DeviceSensor> sensors = new ArrayList<DeviceSensor>();
		for(DeviceSensorInfo device : list) {
			if(StringUtil.isNotBlank(device.getMac())) {
				DeviceSensor deviceSensor = deviceSensorService.findByMac(device.getMac());
				deviceSensor.setBaseEnergy(device.getParkNumber());
				if(deviceSensor!=null) {
					deviceSensor.setCreateTime(deviceSensor.getLastSeenTime());
//					deviceSensor.setDesc(logSensorHeart.getRssi());
				}
				sensors.add(deviceSensor);
			}
			
			
		}
		return sensors;
	}
	
}