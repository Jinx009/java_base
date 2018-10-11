package service.basicFunctions.parking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingPosDeviceDao;
import database.models.parking.ParkingPosDevice;

@Service
public class ParkingPosDeviceService {

	@Autowired
	private ParkingPosDeviceDao parkingPosDeviceDao;
	
	
	public List<ParkingPosDevice> list(){
		return parkingPosDeviceDao.findAll();
	}
	
	public void update(Integer id,Integer status){
		ParkingPosDevice parkingPosDevice = parkingPosDeviceDao.find(id);
		parkingPosDevice.setStatus(status);
		parkingPosDeviceDao.update(parkingPosDevice);
	}
	
}

