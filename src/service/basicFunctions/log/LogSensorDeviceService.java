package service.basicFunctions.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.log.LogSensorDeviceDao;

@Service
public class LogSensorDeviceService {

	@Autowired
	private LogSensorDeviceDao logSensorDeviceDao;
	
	public List<String> findAlive(){
		return logSensorDeviceDao.findAlive();
	}
	
}
