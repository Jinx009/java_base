package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssLogDao;
import database.model.GnssLog;

@Service
public class GnssLogService {

	@Autowired
	private GnssLogDao gnssLogDao;
	
	public PageDataList<GnssLog> find(Integer p){
		return gnssLogDao.findByPage(p);
	}
	
	public void save(GnssLog gnssLog) {
		gnssLogDao.save(gnssLog);
	}

	public List<GnssLog> getByDate(String startDate, String endDate,String mac, Integer fixType, Integer fixStatus, Integer horMin, Integer horMax, String type) {
		return gnssLogDao.getByDate(startDate,endDate,mac,fixType,fixStatus,horMin,horMax,type) ;
	}

	public PageDataList<GnssLog> openLogData(int i, String mac) {
		return gnssLogDao.openLogData(1,mac);
	}
}