package service.basicFunctions.device;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.device.DeviceRouterDao;
import database.common.PageDataList;
import database.models.device.DeviceRouter;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
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
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			if(p==null||p==0){
				p = 1;
			}
			PageDataList<DeviceRouter> list = deviceRouterDao.findAll(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
