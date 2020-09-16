package service.basicFunctions.device;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.basicFunctions.dao.business.BusinessLocationDao;
import database.basicFunctions.dao.device.DeviceJobDao;
import database.basicFunctions.dao.device.DeviceSensorDao;
import database.common.PageDataList;
import database.models.business.BusinessArea;
import database.models.business.BusinessLocation;
import database.models.device.DeviceJob;
import database.models.device.DeviceSensor;
import database.models.device.vo.DeviceSensorVo;
import service.basicFunctions.BaseService;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceSensorService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceRouterService.class);

	@Autowired
	private DeviceSensorDao deviceSensorDao;
	@Autowired
	private BusinessAreaDao businessAreaDao;
	@Autowired
	private BusinessLocationDao businessLocationDao;
	@Autowired
	private DeviceJobDao deviceJobDao;
	
	/**
	 * 批量操作地磁列表
	 * @return
	 */
	public List<DeviceSensor> findAll(){
		return deviceSensorDao.findAll();
	}
	
	/**
	 * 地磁分页列表
	 * @param params
	 * @return
	 */
	public Resp<?> list(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			Integer areaId = jsonObject.getInteger(BaseConstant.AREA_ID);
			String mac = jsonObject.getString(BaseConstant.MAC);
			if(p==null||p==0){
				p = 1;
			}
			PageDataList<DeviceSensor> list = deviceSensorDao.findUse(p,areaId,mac);
			PageDataList<DeviceSensorVo> vos = new PageDataList<DeviceSensorVo>(); 
			List<DeviceSensorVo> vo = new ArrayList<DeviceSensorVo>();
			vos.setPage(list.getPage());
			if(list!=null&&list.getList()!=null&&!list.getList().isEmpty()){
				for(DeviceSensor deviceSensor : list.getList()){
					DeviceSensorVo deviceSensorVo = DeviceSensorVo.instance(deviceSensor);
					if(deviceSensor.getAreaId()!=null){
						BusinessArea businessArea = businessAreaDao.find(deviceSensor.getAreaId());
						BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
						deviceSensorVo.setAreaName(businessArea.getName());
						deviceSensorVo.setLocationName(businessLocation.getName());
					}
					vo.add(deviceSensorVo);
				}
			}
			vos.setList(vo);
			resp = new Resp<>(vos);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}

	
	/**
	 * 地磁列表
	 * @param params
	 * @return
	 */
	public Resp<?> all(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer areaId = jsonObject.getInteger(BaseConstant.AREA_ID);
			String mac = jsonObject.getString(BaseConstant.MAC);
			List<DeviceSensor> list = deviceSensorDao.findAllUse(areaId,mac);
			List<DeviceSensorVo> vo = new ArrayList<DeviceSensorVo>();
			if(list!=null&&!list.isEmpty()){
				for(DeviceSensor deviceSensor : list){
					DeviceSensorVo deviceSensorVo = DeviceSensorVo.instance(deviceSensor);
					if(deviceSensor.getAreaId()!=null){
						BusinessArea businessArea = businessAreaDao.find(deviceSensor.getAreaId());
						BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
						deviceSensorVo.setAreaName(businessArea.getName());
						deviceSensorVo.setLocationName(businessLocation.getName());
					}
					vo.add(deviceSensorVo);
				}
			}
			resp = new Resp<>(vo);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * 地磁详情
	 * @param params
	 * @return
	 */
	public Resp<?> detail(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String mac = jsonObject.getString(BaseConstant.MAC);
			if(StringUtil.isNotBlank(mac)){
				DeviceSensor deviceSensor = deviceSensorDao.findByMac(mac);
				DeviceSensorVo deviceSensorVo = DeviceSensorVo.instance(deviceSensor);
				if(deviceSensor.getAreaId()!=null){
					BusinessArea businessArea = businessAreaDao.find(deviceSensor.getAreaId());
					BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
					deviceSensorVo.setAreaName(businessArea.getName());
					deviceSensorVo.setLocationName(businessLocation.getName());
				}
				resp = new Resp<>(deviceSensorVo);
				return resp;
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * 地磁设置区域
	 * @param params
	 * @return
	 */
	public Resp<?> setArea(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			List<String> macs = JSON.parseArray(jsonObject.getString(BaseConstant.MAC),String.class);
			Integer areaId = jsonObject.getInteger(BaseConstant.AREA_ID);
			for(String mac : macs){
				DeviceSensor deviceSensor = deviceSensorDao.findByMac(mac);
				deviceSensor.setAreaId(areaId);
				deviceSensorDao.update(deviceSensor);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * 地磁设置区域
	 * @param params
	 * @return
	 */
	public Resp<?> setUpdate(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String mac = jsonObject.getString(BaseConstant.MAC);
			String jobDetail = jsonObject.getString(BaseConstant.JOB_DETAIL);
			String cmd = jsonObject.getString(BaseConstant.CMD);
			DeviceSensor deviceSensor = deviceSensorDao.findByMac(mac);
			List<DeviceJob> list = deviceJobDao.findByTarget(deviceSensor.getRouterMac());
			if(list!=null&&!list.isEmpty()){
				resp.setMsg(BaseConstant.JOB_NOT_DONE);
				return resp;
			}else{
				
			}
			deviceJobDao.save(deviceSensor,cmd,jobDetail);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}

	public DeviceSensor findByMac(String mac) {
		return deviceSensorDao.findByMac(mac);
	}

	public void save(DeviceSensor sensor) {
		deviceSensorDao.save(sensor);
	}

	public void update(DeviceSensor sensor) {
		deviceSensorDao.update(sensor);
	}

	public List<DeviceSensor> getSensorsByArea(int i) {
		return deviceSensorDao.getSensorsByArea(i);
	}
	
	public List<DeviceSensor> findByParentMac(String parentMac) {
		return deviceSensorDao.findByParentMacList(parentMac);
	}

	public List<DeviceSensor> install() {
		return deviceSensorDao.install();
	}

	public List<String> findParentMacByLike(String address) {
		return deviceSensorDao.findParentMacByLike(address);
	}

	public List<DeviceSensor> findByMacLike(String mac) {
		return deviceSensorDao.findByMacLike(mac);
	}
	
}
