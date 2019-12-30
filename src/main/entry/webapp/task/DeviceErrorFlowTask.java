package main.entry.webapp.task;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.basicFunctions.dao.device.DeviceErrorFlowDao;
import database.basicFunctions.dao.device.DeviceSensorDao;
import database.models.device.DeviceErrorFlow;
import database.models.device.DeviceSensor;

@Component
@Lazy(value=false)
public class DeviceErrorFlowTask {
	
	private static final Logger log = LoggerFactory.getLogger(DeviceErrorFlowTask.class);
	
	@Autowired
	private DeviceErrorFlowDao DeviceErrorFlowDao;
	@Autowired
	private DeviceSensorDao deviceSensorDao;

//	@Scheduled(cron = "0 */10 * * * ?")//XX分钟处理一次
	public void init(){
		try {
			List<DeviceErrorFlow> list = DeviceErrorFlowDao.findUse();
			if(list!=null&&!list.isEmpty()){
				for(DeviceErrorFlow deviceErrorFlow : list){
					DeviceSensor deviceSensor = deviceSensorDao.findByMac(deviceErrorFlow.getMac());
					if(deviceSensor==null){
						deviceSensor = deviceSensorDao.findByParentMac(deviceErrorFlow.getMac());
						if(deviceSensor==null){
							deviceSensor = deviceSensorDao.findByRouterMac(deviceErrorFlow.getMac());
						}
					}
					if(deviceSensor!=null){
						if(deviceSensor.getAreaId()!=null){
							deviceErrorFlow.setAreaId(deviceSensor.getAreaId());
						}
					}else{
						deviceErrorFlow.setAreaId(9999);
					}
					DeviceErrorFlowDao.update(deviceErrorFlow);
				}
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
     }
	
}
