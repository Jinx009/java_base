package service;

import org.springframework.stereotype.Service;

import database.dao.IoTCloudLogDao;
import database.models.IotCloudLog;

@Service
public class IoTCloudLogService {

	private IoTCloudLogDao ioTCloudLogDao;
	
	public void save(IotCloudLog iotCloudLog){
		ioTCloudLogDao.save(iotCloudLog);
	}
	
}
