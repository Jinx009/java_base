package service.basicFunctions.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.device.DeviceRepeaterDao;
import database.models.device.DeviceRepeater;
import database.models.device.DeviceSensor;

@Service
public class DeviceRepeaterService {

	@Autowired
	private DeviceRepeaterDao deviceRepeaterDao;
	
	public void update(DeviceRepeater deviceRepeater){
		deviceRepeaterDao.update(deviceRepeater);
	}
	
	public DeviceRepeater findByMac(String mac){
		return deviceRepeaterDao.findByMac(mac);
	}
	
	public void save(DeviceRepeater deviceRepeater){
		deviceRepeaterDao.save(deviceRepeater);
	}

	public void saveNew(DeviceSensor deviceSensor) {
		DeviceRepeater deviceRepeater = new DeviceRepeater();
		deviceRepeater.setAreaId(deviceSensor.getAreaId());
		deviceRepeater.setMac(deviceSensor.getParentMac());
		deviceRepeater.setRecSt(1);
		deviceRepeater.setRouterMac(deviceSensor.getRouterMac());
		deviceRepeaterDao.save(deviceRepeater);
	}
	
}
