package service.basicFunctions.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProBluetoothDeviceDao;
import database.models.pro.ProBluetoothDevice;

@Service
public class ProBluetoothDeviceService {

	@Autowired
	private ProBluetoothDeviceDao proBluetoothDeviceDao;
	
	public List<ProBluetoothDevice> findAll(){
		return proBluetoothDeviceDao.findAll();
	}

	public String findCarNo(String uuid) {
		return proBluetoothDeviceDao.findCarNo(uuid);
	}
	
}
