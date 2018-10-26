package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewaySmokeDeviceDao;
import database.models.project.ProGatewaySmokeDevice;

@Service
public class ProGatewaySmokeDeviceService {

	@Autowired
	private ProGatewaySmokeDeviceDao proGatewaySmokeDeviceDao;
	
	public ProGatewaySmokeDevice findByMac(String mac){
		return proGatewaySmokeDeviceDao.findByMac(mac);
	}
	
	public void save(String mac){
		ProGatewaySmokeDevice proGatewaySmokeDevice = new ProGatewaySmokeDevice();
		proGatewaySmokeDevice.setCreateTime(new Date());
		proGatewaySmokeDevice.setMac(mac);
		proGatewaySmokeDevice.setSendUrl("");
		proGatewaySmokeDeviceDao.save(proGatewaySmokeDevice);
	}
	
}
