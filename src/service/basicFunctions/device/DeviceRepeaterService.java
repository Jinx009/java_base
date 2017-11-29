package service.basicFunctions.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.basicFunctions.dao.device.DeviceRepeaterDao;
import database.common.PageDataList;
import database.models.business.BusinessArea;
import database.models.device.DeviceRepeater;
import database.models.device.DeviceSensor;
import service.basicFunctions.BaseService;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceRepeaterService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceRepeaterService.class);

	@Autowired
	private DeviceRepeaterDao deviceRepeaterDao;
	@Autowired
	private BusinessAreaDao businessAreaDao;
	
	public void update(DeviceRepeater deviceRepeater){
		deviceRepeaterDao.update(deviceRepeater);
	}
	
	public DeviceRepeater findByMac(String mac){
		return deviceRepeaterDao.findByMac(mac);
	}
	
	public void save(DeviceRepeater deviceRepeater){
		deviceRepeaterDao.save(deviceRepeater);
	}

	public void saveNew(DeviceSensor deviceSensor) {
		DeviceRepeater deviceRepeater = new DeviceRepeater();
		if(deviceSensor.getAreaId()!=null){
			BusinessArea businessArea = businessAreaDao.find(deviceSensor.getAreaId());
			deviceRepeater.setAreaName(businessArea.getName());
			deviceRepeater.setAreaId(deviceSensor.getAreaId());
		}
		if(StringUtil.isNotBlank(deviceSensor.getParentMac())){
			deviceRepeater.setMac(deviceSensor.getParentMac());
			deviceRepeater.setRecSt(1);
			deviceRepeater.setRouterMac(deviceSensor.getRouterMac());
			deviceRepeaterDao.save(deviceRepeater);
		}
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
			PageDataList<DeviceRepeater> list = deviceRepeaterDao.findAll(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
