package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssBaseLogDao;
import database.model.GnssBaseLog;

@Service
public class GnssBaseLogService {

	@Autowired
	private GnssBaseLogDao gnssBaseLogDao;
	
	public PageDataList<GnssBaseLog> findByPage(Integer p){
		return gnssBaseLogDao.findByPage(1);
	}
	
	public void save(GnssBaseLog gnssBaseLog){
		gnssBaseLogDao.save(gnssBaseLog);
	}
	
}
