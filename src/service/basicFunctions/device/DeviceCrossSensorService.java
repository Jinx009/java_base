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
import database.basicFunctions.dao.device.DeviceCrossSensorDao;
import database.common.PageDataList;
import database.models.business.BusinessArea;
import database.models.business.BusinessLocation;
import database.models.device.DeviceCrossSensor;
import database.models.device.vo.DeviceCrossSensorVo;
import service.basicFunctions.BaseService;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceCrossSensorService extends BaseService{

	private static final Logger log = LoggerFactory.getLogger(DeviceCrossSensorService.class);
	
	@Autowired
	private DeviceCrossSensorDao deviceCrossSensorDao;
	@Autowired
	private BusinessAreaDao businessAreaDao;
	@Autowired
	private BusinessLocationDao businessLocationDao;
	
	/**
	 * 路口地磁列表
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
			Integer locationId = jsonObject.getInteger(BaseConstant.LOCATION_ID);
			String mac = jsonObject.getString(BaseConstant.MAC);
			if(p==null||p==0){
				p = 1;
			}
			PageDataList<DeviceCrossSensor> list = deviceCrossSensorDao.getPage(p,areaId,mac,locationId);
			PageDataList<DeviceCrossSensorVo> vos = new PageDataList<DeviceCrossSensorVo>(); 
			List<DeviceCrossSensorVo> vo = new ArrayList<DeviceCrossSensorVo>();
			vos.setPage(list.getPage());
			if(list!=null&&list.getList()!=null&&!list.getList().isEmpty()){
				for(DeviceCrossSensor deviceCrossSensor : list.getList()){
					DeviceCrossSensorVo deviceCrossSensorVo = DeviceCrossSensorVo.instance(deviceCrossSensor);
					if(deviceCrossSensor.getAreaId()!=null){
						BusinessArea businessArea = businessAreaDao.find(deviceCrossSensor.getAreaId());
						BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
						deviceCrossSensorVo.setAreaName(businessArea.getName());
						deviceCrossSensorVo.setLocationName(businessLocation.getName());
					}
					vo.add(deviceCrossSensorVo);
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
	 * 路口地磁列表
	 * @param params
	 * @return
	 */
	public Resp<?> all(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			List<DeviceCrossSensor> list = deviceCrossSensorDao.findAllUse();
			List<DeviceCrossSensorVo> list2 = new ArrayList<DeviceCrossSensorVo>(); 
			if(list!=null&&!list.isEmpty()){
				for(DeviceCrossSensor deviceCrossSensor : list){
					DeviceCrossSensorVo deviceCrossSensorVo = DeviceCrossSensorVo.instance(deviceCrossSensor);
					if(deviceCrossSensor.getAreaId()!=null){
						BusinessArea businessArea = businessAreaDao.find(deviceCrossSensor.getAreaId());
						BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
						deviceCrossSensorVo.setAreaName(businessArea.getName());
						deviceCrossSensorVo.setLocationName(businessLocation.getName());
					}
					list2.add(deviceCrossSensorVo);
				}
			}
			resp = new Resp<>(list2);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * 路口地磁详情
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
				DeviceCrossSensor deviceCrossSensor = deviceCrossSensorDao.findByMac(mac);
				DeviceCrossSensorVo deviceCrossSensorVo = DeviceCrossSensorVo.instance(deviceCrossSensor);
				if(deviceCrossSensor.getAreaId()!=null){
					BusinessArea businessArea = businessAreaDao.find(deviceCrossSensor.getAreaId());
					BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
					deviceCrossSensorVo.setAreaName(businessArea.getName());
					deviceCrossSensorVo.setLocationName(businessLocation.getName());
				}
				resp = new Resp<>(deviceCrossSensorVo);
				return resp;
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
