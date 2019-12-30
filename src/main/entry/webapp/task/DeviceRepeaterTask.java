package main.entry.webapp.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.device.DeviceRepeater;
import database.models.device.DeviceRouter;
import database.models.device.DeviceSensor;
import service.basicFunctions.device.DeviceRepeaterService;
import service.basicFunctions.device.DeviceRouterService;
import service.basicFunctions.device.DeviceSensorService;

@Component
@Lazy(value=false)
public class DeviceRepeaterTask {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceRepeaterTask.class);
	
	@Autowired
	private DeviceRepeaterService deviceRepeaterService;
	@Autowired
	private DeviceSensorService deviceSensorService;
	@Autowired
	private DeviceRouterService deviceRouterService;
	
	
	/**
	 * 每天中午12点执行一次
	 * 获取repeater
	 * 
	 */
//	@Scheduled(cron = "0 0 12 * * ?")
	public void init(){
		try {
			List<DeviceSensor> sensors = deviceSensorService.findAll();
			if(sensors!=null&&!sensors.isEmpty()){
				for(DeviceSensor deviceSensor : sensors){
					DeviceRepeater deviceRepeater = deviceRepeaterService.findByMac(deviceSensor.getParentMac());
					DeviceRouter deviceRouter = deviceRouterService.findByMac(deviceSensor.getParentMac());
					if(deviceRouter==null&&deviceRepeater==null){
						deviceRepeaterService.saveNew(deviceSensor);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
     }
	
}
