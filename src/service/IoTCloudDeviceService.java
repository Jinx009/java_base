package service;

import org.springframework.stereotype.Service;

import database.dao.IoTCloudDeviceDao;
import database.models.IoTCloudDevice;

@Service
public class IoTCloudDeviceService {

	private IoTCloudDeviceDao ioTCloudDeviceDao;
	
	public void save(IoTCloudDevice ioTCloudDevice){
		ioTCloudDeviceDao.save(ioTCloudDevice);
	}
	
	public String getByImei(String imei){
		IoTCloudDevice ioTCloudDevice = ioTCloudDeviceDao.findByImei(imei);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice.getMac();
		}
		return "";
	}

	public IoTCloudDevice findByMac(String mac) {
		IoTCloudDevice ioTCloudDevice = ioTCloudDeviceDao.findByMac(mac);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice;
		}
		return null;
	}
	
}
