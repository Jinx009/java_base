package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkBatteryLogDao;
import database.model.GnssRtkBatteryLog;

@Service
public class GnssRtkBatteryLogService {

	@Autowired
	private GnssRtkBatteryLogDao gnssRtkBatteryLogDao;
	
	public void save(GnssRtkBatteryLog gnssRtkBatteryLog) {
		gnssRtkBatteryLogDao.save(gnssRtkBatteryLog);
	}
	
	public PageDataList<GnssRtkBatteryLog> findByPage(int p,String imei){
		return gnssRtkBatteryLogDao.findByPage(p,imei);
	}
	
}
