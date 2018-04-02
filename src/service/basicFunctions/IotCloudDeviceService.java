package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.IotCloudDeviceDao;
import database.models.IoTCloudDevice;

@Service
public class IotCloudDeviceService {

	@Autowired
	private IotCloudDeviceDao iotCloudDeviceDao;
	
	public void save(IoTCloudDevice ioTCloudDevice){
		iotCloudDeviceDao.save(ioTCloudDevice);
	}
	
	public String getByImei(String imei){
		IoTCloudDevice ioTCloudDevice = iotCloudDeviceDao.findByImei(imei);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice.getMac();
		}
		return "";
	}

	public IoTCloudDevice findByMac(String mac) {
		IoTCloudDevice ioTCloudDevice = iotCloudDeviceDao.findByMac(mac);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice;
		}
		return null;
	}

	public IoTCloudDevice findByDeviceId(String deviceId) {
		IoTCloudDevice ioTCloudDevice = iotCloudDeviceDao.findByDeviceId(deviceId);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice;
		}
		return null;
	}
	
	public PageDataList<IoTCloudDevice> pageList(Integer p){
		return iotCloudDeviceDao.findAll(p);
	}
	
}
