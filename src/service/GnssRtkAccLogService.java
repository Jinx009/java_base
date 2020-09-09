package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkAccLogDao;
import database.model.GnssRtkAccLog;

@Service
public class GnssRtkAccLogService {

	@Autowired
	private GnssRtkAccLogDao gnssRtkAccLogDao;
	
	public void save(GnssRtkAccLog gnssRtkAccLog) {
		gnssRtkAccLogDao.save(gnssRtkAccLog);
	}

	public PageDataList<GnssRtkAccLog> findByPageAndMac(int p, String mac) {
		return gnssRtkAccLogDao.findByPageAndMac(p,mac);
	}
	
}
