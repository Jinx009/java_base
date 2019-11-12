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
	
	
	public void save(ParkingVedio parkingVedio) {
		parkingVedioDao.save(parkingVedio);
	}

	public void update(ParkingVedio parkingVedio) {
		parkingVedioDao.update(parkingVedio);
	}

	public List<ParkingVedio> findByStatus(Integer status) {
		return parkingVedioDao.findByStatus(status);
	}

	public List<ParkingVedio> getNotOk() {
		return  parkingVedioDao.getNotOk();
	}
	
	
}
