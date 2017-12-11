package database.basicFunctions.dao.device;


import java.util.List;

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

	public void update(String mac, Integer locationId, String note) {
		DeviceRouter deviceRouter = findByMac(mac);
		if(locationId!=0){
			deviceRouter.setLocationId(locationId);
		}
		deviceRouter.setNote(note);
		update(deviceRouter);
	}

	public List<DeviceRouter> findAllUse() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		return findByCriteria(queryParam);
	}

}
