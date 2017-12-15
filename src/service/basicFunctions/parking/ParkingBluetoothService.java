package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

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
	
	public List<ParkingBluetooth> findAll(){
		return parkingBluetoothDao.findAll();
	}

	public void saveOrUpdate(ParkingBluetooth parkingBluetooth) {
		if(parkingBluetooth.getId()!=null){
			parkingBluetoothDao.update(parkingBluetooth);
		}else{
			parkingBluetoothDao.save(parkingBluetooth);
		}
	}

	public void delete(Integer id) {
		parkingBluetoothDao.delete(id);
	}

	public void saveOrUpdate(String uuid, String carNo, String mobilePhone) {
		ParkingBluetooth parkingBluetooth = parkingBluetoothDao.findByUid(uuid);
		if(parkingBluetooth!=null){
			parkingBluetooth.setUpdateTime(new Date());
			parkingBluetooth.setCarNo(carNo);
			parkingBluetooth.setMobilePhone(mobilePhone);
			parkingBluetoothDao.update(parkingBluetooth);
		}else{
			parkingBluetooth = new ParkingBluetooth();
			parkingBluetooth.setUpdateTime(new Date());
			parkingBluetooth.setCarNo(carNo);
			parkingBluetooth.setCreateTime(new Date());
			parkingBluetooth.setUuid(uuid);
			parkingBluetooth.setStatus(1);
			parkingBluetooth.setMobilePhone(mobilePhone);
			parkingBluetoothDao.save(parkingBluetooth);
		}
		
	}
	
	
}
