package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssRtkHeartLogDao;
import database.model.GnssRtkHeartLog;

@Service
public class GnssRtkHeartLogService {

	@Autowired
	private GnssRtkHeartLogDao gnssRtkHeartLogDao;
	
	public void save(GnssRtkHeartLog gnssRtkHeartLog) {
		gnssRtkHeartLogDao.save(gnssRtkHeartLog);
	}

	public void saveHeartbeat(String payload, String mac) {
		GnssRtkHeartLog log = new GnssRtkHeartLog();
		log.setBaseData(payload);
		log.setMac(mac);
		log.setCreateTime(new Date());
		gnssRtkHeartLogDao.save(log);
	}
	
}
