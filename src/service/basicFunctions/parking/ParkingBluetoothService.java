package service.basicFunctions.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingBluetoothDao;
import database.models.parking.ParkingBluetooth;

@Service
public class ParkingBluetoothService {

	@Autowired
	private ParkingBluetoothDao parkingBluetoothDao;
	
	public void save(ParkingBluetooth parkingBluetooth){
		parkingBluetoothDao.save(parkingBluetooth);
	}
	
	public void update(ParkingBluetooth parkingBluetooth){
		parkingBluetoothDao.update(parkingBluetooth);
	}
	
	public void delete(ParkingBluetooth parkingBluetooth){
		parkingBluetoothDao.delete(parkingBluetooth);
	}
	
	public ParkingBluetooth findByUid(String uuid){
		return parkingBluetoothDao.findByUid(uuid);
	}
	
	
}
