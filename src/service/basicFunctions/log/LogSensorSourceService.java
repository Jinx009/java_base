package service.basicFunctions.log;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.log.LogSensorSourceDao;
import database.models.log.LogSensorSource;

@Service
public class LogSensorSourceService {

	@Autowired
	private LogSensorSourceDao logSensorSourceDao;
	
	public List<LogSensorSource> findByMacAndDate(String dateStr,String mac){
		return logSensorSourceDao.findByMacAndDate(mac, dateStr);
	}
	
	public void save(LogSensorSource logSensorSource) {
		logSensorSourceDao.save(logSensorSource);
	}
	
}
