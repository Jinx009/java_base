package service.basicFunctions;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkingSpaceDao;
import database.models.ParkingSpace;

@Service
public class ParkingSpaceService {

	@Autowired
	private ParkingSpaceDao parkingSpaceDao;
	
	public List<ParkingSpace> findByCameraIndex(String cameraIndex){
		return parkingSpaceDao.findByCameraIndex(cameraIndex);
	}
	
	public void update(Integer id,String plateNumber,Integer status,Date date){
		parkingSpaceDao.updateSpace(id, plateNumber, status, date);
	}

	public ParkingSpace findById(Integer id) {
		return parkingSpaceDao.find(id);
	}
	
	public void update(ParkingSpace parkingSpace) {
		parkingSpaceDao.update(parkingSpace);
	}

}
