package service.basicFunctions.guodong;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.guodong.GuodongJobDao;
import database.models.guodong.GuodongJob;

@Service
public class GuodongJobService {

	@Autowired
	private GuodongJobDao guodongJobDao;
	
	public void update(GuodongJob guodongJob){
		guodongJobDao.update(guodongJob);
	}
	
	public GuodongJob findByDevEui(String devEui){
		return guodongJobDao.findByDevEui(devEui);
	}
	
	public void save(String devEui, String taskId){
		GuodongJob guodongJob = new GuodongJob();
		guodongJob.setCreateTime(new Date());
		guodongJob.setData("\\x480032025200");
		guodongJob.setStatus(0);
		guodongJob.setTaskId(taskId);
		guodongJob.setDevEui(devEui);
		guodongJobDao.save(guodongJob);
	}

	public List<GuodongJob> findAll() {
		return guodongJobDao.findAllByTime();
	}

	public GuodongJob findByTaskId(String taskID) {
		return guodongJobDao.findByTaskId(taskID);
	}
	
}
