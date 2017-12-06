package service.basicFunctions.device;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.basicFunctions.dao.business.BusinessLocationDao;
import database.basicFunctions.dao.device.DeviceSensorDao;
import database.common.PageDataList;
import database.models.business.BusinessArea;
import database.models.business.BusinessLocation;
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
	
	public List<DeviceSensor> findAll(){
		return deviceSensorDao.findAll();
	}
	
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
	
}
