package service.basicFunctions.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.device.DeviceSensorDao;
import database.models.device.DeviceSensor;

@Service
public class DeviceSensorService {

	@Autowired
	private DeviceSensorDao deviceSensorDao;
	
	public List<DeviceSensor> findAll(){
		return deviceSensorDao.findAll();
	}
	
}
