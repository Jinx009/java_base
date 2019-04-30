package service.basicFunctions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkingVedioDao;
import database.models.ParkingVedio;

@Service
public class ParkingVedioService {

	@Autowired
	private ParkingVedioDao parkingVedioDao;
	
	public List<ParkingVedio> findByTime(){
		return parkingVedioDao.findByTime();
	}
	
	public void save(ParkingVedio parkingVedio) {
		parkingVedioDao.save(parkingVedio);
	}
	
	
}
