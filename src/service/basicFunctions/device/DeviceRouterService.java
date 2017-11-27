package service.basicFunctions.device;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.device.DeviceRouterDao;
import database.models.device.DeviceRouter;
import service.basicFunctions.BaseService;
import utils.model.Resp;

@Service
public class DeviceRouterService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceRouterService.class);

	@Autowired
	private DeviceRouterDao deviceRouterDao;
	
	public DeviceRouter findByMac(String mac){
		return deviceRouterDao.findByMac(mac);
	}
	
	public Resp<?> list(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			List<DeviceRouter> list = deviceRouterDao.findAll();
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
