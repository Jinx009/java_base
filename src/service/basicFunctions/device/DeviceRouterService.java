package service.basicFunctions.device;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessLocationDao;
import database.basicFunctions.dao.device.DeviceRouterDao;
import database.common.PageDataList;
import database.models.business.BusinessLocation;
import database.models.device.DeviceRouter;
import database.models.device.vo.DeviceRouterVo;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceRouterService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceRouterService.class);

	@Autowired
	private DeviceRouterDao deviceRouterDao;
	@Autowired
	private BusinessLocationDao businessLocationDao;
	
	public DeviceRouter findByMac(String mac){
		return deviceRouterDao.findByMac(mac);
	}
	
	/**
	 * Router列表
	 * @param params
	 * @return
	 */
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
			PageDataList<DeviceRouterVo> vos = new PageDataList<DeviceRouterVo>();
			List<DeviceRouterVo> vo = new ArrayList<DeviceRouterVo>();
			if(list!=null&&list.getList()!=null&&!list.getList().isEmpty()){
				for(DeviceRouter deviceRouter : list.getList()){
					DeviceRouterVo deviceRouterVo = DeviceRouterVo.instance(deviceRouter);
					if(deviceRouter.getLocationId()!=null){
						BusinessLocation businessLocation = businessLocationDao.find(deviceRouter.getLocationId());
						deviceRouterVo.setLocationName(businessLocation.getName());
					}
					vo.add(deviceRouterVo);
				}
			}
			vos.setPage(list.getPage());
			vos.setList(vo);
			resp = new Resp<>(vos);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Router详情
	 * @param params
	 * @return
	 */
	public Resp<?> detail(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String mac = jsonObject.getString(BaseConstant.MAC);
			DeviceRouter deviceRouter = deviceRouterDao.findByMac(mac);
			resp = new Resp<>(deviceRouter);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Router详情
	 * @param params
	 * @return
	 */
	public Resp<?> edit(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String mac = jsonObject.getString(BaseConstant.MAC);
			Integer locationId = jsonObject.getInteger(BaseConstant.LOCATION_ID);
			String note = jsonObject.getString(BaseConstant.NOTE);
			deviceRouterDao.update(mac,locationId,note);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
