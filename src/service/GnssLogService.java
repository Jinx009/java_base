package service;

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
}
