package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingChargeDao;
import database.models.parking.ParkingCharge;

@Service
public class ParkingChargeService {

	@Autowired
	private ParkingChargeDao parkingChargeDao;
	
	public List<ParkingCharge> findAll(){
		return parkingChargeDao.findAll();
	}
	
	public ParkingCharge save(ParkingCharge parkingCharge){
		parkingCharge.setShowStatus(1);
		parkingCharge.setCreateTime(new Date());
		return parkingChargeDao.save(parkingCharge);
	}

	public void del(Integer id) {
		ParkingCharge parkingCharge = parkingChargeDao.find(id);
		parkingCharge.setShowStatus(0);
		parkingChargeDao.update(parkingCharge);
	}
	
}
