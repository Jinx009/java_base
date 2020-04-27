package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssDeviceDao;
import database.model.GnssDevice;

@Service
public class GnssDeviceService {

	@Autowired
	private GnssDeviceDao gnssDeviceDao;
	
	public List<GnssDevice> findAll(){
		return gnssDeviceDao.findAll();
	}
	
	public void save(GnssDevice gnssDevice) {
		gnssDeviceDao.save(gnssDevice);
	}
	
	public void updateTime(GnssDevice gnssDevice) {
		gnssDevice.setUpdateTime(new Date());
		gnssDeviceDao.update(gnssDevice);
	}
	
	public GnssDevice findByMac(String mac) {
		return gnssDeviceDao.findByMac(mac);
	}

	public List<GnssDevice> openDeviceData() {
		return gnssDeviceDao.openDeviceData();
	}
	
}
