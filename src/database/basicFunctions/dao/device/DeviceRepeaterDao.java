package database.basicFunctions.dao.device;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.device.DeviceRepeater;
import utils.model.BaseConstant;

@Repository
public class DeviceRepeaterDao extends BaseDao<DeviceRepeater>{

	public DeviceRepeater findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac",mac);
		return findByCriteriaForUnique(queryParam);
	}

	public PageDataList<DeviceRepeater> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "areaId");
		return findPageList(queryParam);
	}

}
