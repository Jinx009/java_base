package service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkDeviceDao;
import database.model.GnssRtkDevice;

@Service
public class GnssRtkDeviceService {

	@Autowired
	private GnssRtkDeviceDao gnssRtkDeviceDao;
	
	public void save(GnssRtkDevice gnssRtkDevice){
		gnssRtkDeviceDao.save(gnssRtkDevice);
	}
	
	public GnssRtkDevice findByRoverTag(String roverTag){
		return gnssRtkDeviceDao.findByRoverTag(roverTag);
	}
	
	public GnssRtkDevice findByNewTime() {
		return gnssRtkDeviceDao.findByNewTime();
	}
	
	public GnssRtkDevice findByNew2Time() {
		return gnssRtkDeviceDao.findByNew2Time();
	}

	public GnssRtkDevice findByMac(String mac) {
		return gnssRtkDeviceDao.findByMac(mac);
	}
	
	public void update(GnssRtkDevice device) {
		gnssRtkDeviceDao.update(device);
	}

	public List<GnssRtkDevice> findAll() {
		return gnssRtkDeviceDao.findAll();
	}

	public void saveDevice(String payload) {
		GnssRtkDevice gnssDevice = new GnssRtkDevice();
		gnssDevice.setMac(payload);
		gnssDevice.setUpdatetime("");
		gnssDevice.setCreateTime(new Date());
		gnssDevice.setSwitchType(0);
		gnssDevice.setDataType("");
		gnssDevice.setRovertag(payload);
		gnssRtkDeviceDao.save(gnssDevice);
	}

	public PageDataList<GnssRtkDevice> page(int p) {
		return gnssRtkDeviceDao.page(p);
	}

	
}
