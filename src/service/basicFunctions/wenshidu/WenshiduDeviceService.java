package service.basicFunctions.wenshidu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.wenshidu.WenshiduDeviceDao;
import database.models.wenshidu.WenshiduDevice;

@Service
public class WenshiduDeviceService {
	
	@Autowired
	private WenshiduDeviceDao wenshiduDeviceDao;

	public List<WenshiduDevice> findAll() {
		return wenshiduDeviceDao.findAll();
	}

	public void update(WenshiduDevice device) {
		wenshiduDeviceDao.update(device);
	}

	public WenshiduDevice findByDeviceNumber(String deviceNumber) {
		return wenshiduDeviceDao.findByDeviceNumber(deviceNumber);
	}

}
