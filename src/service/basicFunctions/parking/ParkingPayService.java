package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingPayDao;
import database.models.parking.ParkingPay;

@Service
public class ParkingPayService {

	@Autowired
	private ParkingPayDao parkingPayDao;
	
	public ParkingPay save(ParkingPay parkingPay){
		parkingPay.setCreateTime(new Date());
		parkingPay.setShowStatus(1);
		parkingPay.setStatus(0);
		return parkingPayDao.save(parkingPay);
	}
	
	public List<ParkingPay> findAll(){
		return parkingPayDao.findAll();
	}
	
	public void delete(Integer id){
		ParkingPay parkingPay = parkingPayDao.find(id);
		parkingPay.setShowStatus(0);
		parkingPayDao.update(parkingPay);
	}
	
	public void changeStatus(Integer id,Integer status){
		ParkingPay parkingPay = parkingPayDao.find(id);
		parkingPay.setStatus(status);
		parkingPayDao.update(parkingPay);
	}
	
}
