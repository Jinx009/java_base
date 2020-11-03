package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssRtkNumLogDao;
import database.model.GnssRtkNumLog;

@Service
public class GnssRtkNumLogService {

	@Autowired
	private GnssRtkNumLogDao gnssRtkNumLogDao;
	
	public void save(GnssRtkNumLog log) {
		gnssRtkNumLogDao.save(log);
	}
	
	public GnssRtkNumLog  find(String date,String mac, int startHour) {
		return gnssRtkNumLogDao.find(date,mac,startHour);
	}
	
	public List<GnssRtkNumLog> findByMac(String mac,String start,String end){
		return gnssRtkNumLogDao.findByMac(mac,start,end);
	}

	public void update(GnssRtkNumLog log) {
		gnssRtkNumLogDao.update(log);
	}
	
}
