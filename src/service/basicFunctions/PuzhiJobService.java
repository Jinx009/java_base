package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.PuzhiJobDao;
import database.models.PuzhiJob;

@Service
public class PuzhiJobService {

	@Autowired
	private PuzhiJobDao puzhiJobDao;
	
	public PuzhiJob findByUuid(String uuid){
		return puzhiJobDao.findByUuid(uuid);
	}
	
	public PuzhiJob save(PuzhiJob puzhiJob){
		return puzhiJobDao.save(puzhiJob);
	}

	public int findByMacAndTaskStatus(String mac, int taskStatus) {
		return puzhiJobDao.findByMacAndTaskStatus(mac,taskStatus);
	}

	public void update(PuzhiJob pz) {
		puzhiJobDao.update(pz);
	}

	public PuzhiJob findByTelTaskId(String cammandId) {
		return puzhiJobDao.findByTelTaskId(cammandId);
	}

	public PuzhiJob findByMacAndId(int id, String mac) {
		return puzhiJobDao.findByMacAndId(id,mac);
	}
	
}
