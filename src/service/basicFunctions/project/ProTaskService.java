package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProTaskDao;
import database.common.PageDataList;
import database.models.project.ProTask;

@Service
public class ProTaskService {

	@Autowired
	private ProTaskDao proTaskDao;
	
	public PageDataList<ProTask> homeList(Integer p,Integer status,String driverName,String fromDate,String endDate) {
		return proTaskDao.homeList(p, status, driverName, fromDate, endDate);
	}
	
	public List<ProTask> excelList(Integer status,String driverName,String fromDate,String endDate){
		return proTaskDao.excelList(status, driverName, fromDate, endDate);
	}
	
	public void save(ProTask proTask){
		proTaskDao.save(proTask);
	}
	
	public void update(ProTask proTask){
		proTaskDao.update(proTask);
	}
	
	public List<ProTask> findWait(String driverMobile){
		return proTaskDao.findWait(driverMobile);
	}
	
}
