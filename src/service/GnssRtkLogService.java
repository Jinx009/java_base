package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkLogDao;
import database.model.GnssRtkLog;

@Service
public class GnssRtkLogService {

	@Autowired
	private GnssRtkLogDao gnssRtkLogDao;
	
	public void save(GnssRtkLog gnssRtkLog){
		gnssRtkLogDao.save(gnssRtkLog);
	}

	public PageDataList<GnssRtkLog> findByPage(Integer p) {
		return gnssRtkLogDao.pages(p);
	}
	
}
