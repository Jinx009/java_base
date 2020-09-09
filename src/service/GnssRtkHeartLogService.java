package service;

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
	
}
