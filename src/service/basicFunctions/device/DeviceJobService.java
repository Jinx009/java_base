package service.basicFunctions.device;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.device.DeviceJobDao;
import database.basicFunctions.dao.device.DeviceSensorDao;
import database.common.PageDataList;
import database.models.device.DeviceJob;
import database.models.device.DeviceSensor;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceJobService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceJob.class);

	@Autowired
	private DeviceJobDao deviceJobDao;
	@Autowired
	private DeviceSensorDao deviceSensorDao;
	
	public Resp<?> list(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			if(p==null||p==0){
				p = 1;
			}
			PageDataList<DeviceJob> list = deviceJobDao.findAll(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	public Resp<?> create(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String mac = jsonObject.getString(BaseConstant.MAC);
			String cmd = jsonObject.getString(BaseConstant.CMD);
			String jobDetail = jsonObject.getString(BaseConstant.JOB_DETAIL);
			DeviceSensor deviceSensor = deviceSensorDao.findByMac(mac);
			List<DeviceJob> list = deviceJobDao.findByTarget(deviceSensor.getRouterMac());
			if(list!=null&&!list.isEmpty()){
				resp.setMsg(BaseConstant.JOB_NOT_DONE);
				return resp;
			}else{
				if(BaseConstant.CFG_LOCK.equals(cmd)){
					JSONObject jsonObject2 = JSONObject.parseObject(jobDetail);
					Integer data = jsonObject2.getInteger(BaseConstant.DATA);
					if(data==1&&deviceSensor.getAvailable()==1){
						resp.setMsg(BaseConstant.CFG_LOCK_HAS_CAR);
						return resp;
					}
				}
				deviceJobDao.save(deviceSensor,cmd,jobDetail);
				resp = new Resp<>(true);
				return resp;
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	public Resp<?> delete(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer id = jsonObject.getInteger(BaseConstant.ID);
			DeviceJob deviceJob = deviceJobDao.find(id);
			if(deviceJob!=null){
				deviceJob.setStatus(3);
				deviceJobDao.update(deviceJob);
				return  new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
