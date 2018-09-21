package service.basicFunctions.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProTaskDao;
import database.basicFunctions.dao.project.ProTaskTitleDao;
import database.common.PageDataList;
import database.models.project.ProTask;

@Service
public class ProTaskService {

	@Autowired
	private ProTaskDao proTaskDao;
	@Autowired
	private ProTaskTitleDao proTaskTitleDao;
	
	public PageDataList<ProTask> homeList(Integer p,Integer status,String driverName,Integer taskTitleId) {
		return proTaskDao.homeList(p, status, driverName,taskTitleId);
	}
	
	public List<ProTask> excelList(Integer titleId){
		return proTaskDao.excelList(titleId);
	}
	
	public void save(ProTask proTask){
		proTaskDao.save(proTask);
	}
	
	public void changeSingleShowStatus(Integer id){
		ProTask proTask = proTaskDao.find(id);
		proTask.setShowStatus(0);
		proTaskDao.update(proTask);
	}
	
	public void changeShowStatus(Integer titleId){
		List<ProTask> list = proTaskDao.findByTitleId(titleId);
		if(list!=null&&!list.isEmpty()){
			for(ProTask proTask:list){
				proTask.setShowStatus(0);
				proTaskDao.update(proTask);
			}
		}
	}
	
	public void update(ProTask proTask){
		proTaskDao.update(proTask);
	}
	
	public List<ProTask> findWait(String driverMobile){
		return proTaskDao.findWait(driverMobile);
	}
	
	public void changeStatus(Integer id,String name,String driverMobile){
		ProTask proTask = proTaskDao.find(id);
		proTask.setStatus(1);
		proTask.setDriverName(name);
		proTask.setDriverMobile(driverMobile);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		proTask.setPickedTime(sdf.format(new Date()).substring(11, 16));
		proTaskDao.update(proTask);
	}

	public void save(String noId,String name,String dep,String description,String flight,String pickTime,String driverName,String driverMobile,Integer taskTitleId, String dateStr) {
		try {
			ProTask proTask = new ProTask();
			proTask.setNoId(noId);
			proTask.setName(name);
			proTask.setDep(dep);
			proTask.setDescription(description);
			proTask.setFlight(flight);
			proTask.setPickTime(pickTime);
			proTask.setPickedTime("");
			proTask.setDriverMobile(driverMobile);
			proTask.setDriverName(driverName);
			proTask.setMailTime("");
			proTask.setTaskTitle(proTaskTitleDao.find(taskTitleId).getName());
			proTask.setTaskTitleId(taskTitleId);
			proTask.setDateStr(dateStr);
			proTask.setCreateTime(new Date());
			proTask.setStatus(0);
			proTask.setShowStatus(1);
			proTaskDao.save(proTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(String mailTime, String dep, String description, String name, String number, String pickedTime,
			String pickTime, String flight, String driverName, String driverMobile, Integer id) {
		try {
			ProTask proTask = proTaskDao.find(id);
			proTask.setDep(dep);
			proTask.setDescription(description);
			proTask.setMailTime(mailTime);
			proTask.setPickedTime(pickedTime);
			proTask.setPickTime(pickTime);
			proTask.setDriverName(driverName);
			proTask.setDriverMobile(driverMobile);
			proTask.setFlight(flight);
			proTask.setName(name);
			proTaskDao.update(proTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProTask find(Integer id) {
		return proTaskDao.find(id);
	}

	public PageDataList<ProTask> frontWaitList(String mobilePhone, Integer p) {
		return proTaskDao.frontWaitList(p,mobilePhone);
	}

	public PageDataList<ProTask> frontDoneList(String mobilePhone, Integer p) {
		return proTaskDao.frontDoneList(p,mobilePhone);
	}
	
}
