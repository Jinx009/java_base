package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.IoTCloudEventLogDao;
import database.models.IoTCloudEventLog;

@Service
public class IoTCloudEventLogService {

	@Autowired
	private IoTCloudEventLogDao ioTCloudEventLogDao;
	
	public void save(IoTCloudEventLog ioTCloudEventLog) {
		ioTCloudEventLogDao.save(ioTCloudEventLog);
	}
	
	public void update(IoTCloudEventLog ioTCloudEventLog) {
		ioTCloudEventLogDao.update(ioTCloudEventLog);
	}
	
}
