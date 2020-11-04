package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkConnLogDao;
import database.model.GnssRtkConnLog;

@Service
public class GnssRtkConnLogService {

	@Autowired
	private GnssRtkConnLogDao gnssRtkConnLogDao;
	
	public PageDataList<GnssRtkConnLog> findByMacAndP(int p,String mac){
		return gnssRtkConnLogDao.findByMacAndP(p,mac);
	}

	public void save(GnssRtkConnLog connLog) {
		gnssRtkConnLogDao.save(connLog);
	}

	public void saveConnLog(String payload, int i) {
		GnssRtkConnLog connLog = new GnssRtkConnLog();
		connLog.setCreateTime(new Date());
		connLog.setMac(payload);
		connLog.setType(i);
		gnssRtkConnLogDao.save(connLog);
	}
	
}
