package database.basicFunctions.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.device.DeviceRouter;

@Repository
public class DeviceRouterDao extends BaseDao<DeviceRouter>{

	public DeviceRouter findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}
	
	public List<DeviceRouter> findAll(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"lastSeenTime");
		queryParam.addParam("recSt", 1);
		return findByCriteria(queryParam);
	}

}
