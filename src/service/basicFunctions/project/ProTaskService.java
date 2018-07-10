package service.basicFunctions.project;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	public void changeStatus(Integer id){
		ProTask proTask = proTaskDao.find(id);
		proTask.setStatus(1);
		proTask.setPickedTime(new Date());
		proTaskDao.update(proTask);
	}

	public void save(String mobilePhone, String dep, String description, String name, String number, String pickDate,
			String pickTime, String flight, String driverName, String driverMobile) {
		String date = pickDate+" "+pickTime;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(date);
			ProTask proTask = new ProTask();
			proTask.setCreateTime(new Date());
			proTask.setDep(dep);
			proTask.setDescription(description);
			proTask.setDriverName(driverName);
			proTask.setDriverMobile(driverMobile);
			proTask.setFlight(flight);
			proTask.setMobilePhone(mobilePhone);
			proTask.setName(name);
			proTask.setNumber(number);
			proTask.setPickTime(d);
			proTask.setStatus(0);
			proTaskDao.save(proTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(String mobilePhone, String dep, String description, String name, String number, String pickDate,
			String pickTime, String flight, String driverName, String driverMobile,Integer id) {
		String date = pickDate+" "+pickTime;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(date);
			ProTask proTask = proTaskDao.find(id);
			proTask.setDep(dep);
			proTask.setDescription(description);
			proTask.setDriverName(driverName);
			proTask.setDriverMobile(driverMobile);
			proTask.setFlight(flight);
			proTask.setMobilePhone(mobilePhone);
			proTask.setName(name);
			proTask.setNumber(number);
			proTask.setPickTime(d);
			proTaskDao.update(proTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProTask find(Integer id) {
		return proTaskDao.find(id);
	}
	
}
