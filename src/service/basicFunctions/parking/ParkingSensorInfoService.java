package service.basicFunctions.parking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingSensorInfoDao;
import database.models.parking.ParkingSensorInfo;

@Service
public class ParkingSensorInfoService {

	@Autowired
	private ParkingSensorInfoDao parkingSensorInfoDao;
	
	public List<ParkingSensorInfo> list(){
		return parkingSensorInfoDao.findAllSensor();
	}
	
}
