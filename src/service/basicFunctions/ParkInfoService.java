package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkInfoDao;
import database.models.ParkInfo;

@Service
public class ParkInfoService {

	@Autowired
	private ParkInfoDao parkInfoDao;
	
	public Integer getMaxBaseId(){
		return parkInfoDao.getMaxBaseId();
	}

	public void save(ParkInfo parkInfo) {
		parkInfoDao.save(parkInfo);
	}
	
}
