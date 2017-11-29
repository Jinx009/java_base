package database.basicFunctions.dao.device;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.device.DeviceRouter;
import utils.model.BaseConstant;

@Repository
public class DeviceRouterDao extends BaseDao<DeviceRouter>{

	public DeviceRouter findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}
	
	public PageDataList<DeviceRouter> findAll(int p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "lastSeenTime");
		return findPageList(queryParam);
	}

}