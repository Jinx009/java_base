package service.basicFunctions.device;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.basicFunctions.dao.device.DeviceErrorFlowDao;
import database.common.PageDataList;
import database.models.business.BusinessArea;
import database.models.device.DeviceErrorFlow;
import database.models.device.vo.DeviceErrorFlowVo;
import service.basicFunctions.BaseService;
import utils.enums.ErrorCode;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceErrorFlowService extends BaseService{

	private static final Logger log = LoggerFactory.getLogger(DeviceErrorFlowService.class);
	
	@Autowired
	private DeviceErrorFlowDao deviceErrorFlowDao;
	@Autowired
	private BusinessAreaDao businessAreaDao;
	
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
			PageDataList<DeviceErrorFlow> list = deviceErrorFlowDao.getPage(p,areaId,mac);
			PageDataList<DeviceErrorFlowVo> vo = new PageDataList<DeviceErrorFlowVo>();
			vo.setPage(list.getPage());
			vo.setType(list.getType());
			List<DeviceErrorFlowVo> vos = new ArrayList<DeviceErrorFlowVo>();
			if(list!=null&&list.getList()!=null&&!list.getList().isEmpty()){
				for(DeviceErrorFlow deviceErrorFlow : list.getList()){
					DeviceErrorFlowVo deviceErrorFlowVo = new DeviceErrorFlowVo();
					deviceErrorFlowVo.setDeviceErrorFlow(deviceErrorFlow);
					if(deviceErrorFlow.getAreaId()!=null){
						BusinessArea businessArea = businessAreaDao.find(deviceErrorFlow.getAreaId());
						if(businessArea!=null){
							deviceErrorFlowVo.setAreaName(businessArea.getName());
							deviceErrorFlowVo.setTypeName(getTypeName(deviceErrorFlow.getType()));
						}
					}
					ErrorCode errorCode = ErrorCode.getByCode(deviceErrorFlow.getErrorBitMap());
					if(errorCode!=null){
						deviceErrorFlowVo.setErrorName(errorCode.getErrorInfo());
						deviceErrorFlowVo.setSolution(errorCode.getSolution());
					}
					vos.add(deviceErrorFlowVo);
				}
				vo.setList(vos);
			}
			resp = new Resp<>(vo);
			return resp;
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
			DeviceErrorFlow deviceErrorFlow = deviceErrorFlowDao.find(id);
			if(deviceErrorFlow!=null){
				deviceErrorFlow.setStatus(1);
				deviceErrorFlowDao.update(deviceErrorFlow);
				return  new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	
	private String getTypeName(Integer type) {
		if(type == null)
			return null;
		else if(1==type)
			return "接收机";
		else if(2==type)
			return "中继器";
		else if(3==type)
			return "车检器";
		return null;
	}
	
}
