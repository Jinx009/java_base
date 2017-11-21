package service.basicFunctions.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.device.DeviceRouterDao;
import database.models.device.DeviceRouter;

@Service
public class DeviceRouterService {

	@Autowired
	private DeviceRouterDao deviceRouterDao;
	
	public DeviceRouter findByMac(String mac){
		return deviceRouterDao.findByMac(mac);
	}
	
}
