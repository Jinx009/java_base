package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssRtkDebugLogDao;
import database.model.GnssRtkDebugLog;

@Service
public class GnssRtkDebugLogService {

	@Autowired
	private GnssRtkDebugLogDao gnssRtkDebugLogDao;
	
	public void save(GnssRtkDebugLog log) {
		gnssRtkDebugLogDao.save(log);
	}
	
}
