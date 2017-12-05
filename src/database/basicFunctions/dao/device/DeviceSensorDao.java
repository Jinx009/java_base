package database.basicFunctions.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.device.DeviceSensor;
import utils.model.BaseConstant;

@Repository
public class DeviceSensorDao extends BaseDao<DeviceSensor>{

	public List<DeviceSensor> findUse(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findByCriteria(queryParam);
	}
	
	public PageDataList<DeviceSensor> findUse(Integer p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findPageList(queryParam);
	}

	public DeviceSensor findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}

	public DeviceSensor findByParentMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("parentMac", mac);
		List<DeviceSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public DeviceSensor findByRouterMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("routerMac", mac);
		List<DeviceSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
