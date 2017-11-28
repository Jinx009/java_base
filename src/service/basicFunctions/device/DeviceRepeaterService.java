package service.basicFunctions.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.basicFunctions.dao.device.DeviceRepeaterDao;
import database.models.business.BusinessArea;
import database.models.device.DeviceRepeater;
import database.models.device.DeviceSensor;

@Service
public class DeviceRepeaterService {

	@Autowired
	private DeviceRepeaterDao deviceRepeaterDao;
	@Autowired
	private BusinessAreaDao businessAreaDao;
	
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
		BusinessArea businessArea = businessAreaDao.find(deviceSensor.getAreaId());
		deviceRepeater.setAreaId(deviceSensor.getAreaId());
		deviceRepeater.setMac(deviceSensor.getParentMac());
		deviceRepeater.setRecSt(1);
		deviceRepeater.setAreaName(businessArea.getName());
		deviceRepeater.setRouterMac(deviceSensor.getRouterMac());
		deviceRepeaterDao.save(deviceRepeater);
	}
	
}
