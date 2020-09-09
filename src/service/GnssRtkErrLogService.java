package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssRtkErrLogDao;
import database.model.GnssRtkErrLog;

@Service
public class GnssRtkErrLogService {

	@Autowired
	private GnssRtkErrLogDao gnssRtkErrLogDao;
	
	public void save(GnssRtkErrLog gnssRtkErrLog) {
		gnssRtkErrLogDao.save(gnssRtkErrLog);
	}
	
}
