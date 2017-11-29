package service.basicFunctions.device;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.device.DeviceSensorDao;
import database.common.PageDataList;
import database.models.device.DeviceSensor;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceSensorService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceRouterService.class);

	@Autowired
	private DeviceSensorDao deviceSensorDao;
	
	public List<DeviceSensor> findAll(){
		return deviceSensorDao.findAll();
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
			PageDataList<DeviceSensor> list = deviceSensorDao.findUse(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
