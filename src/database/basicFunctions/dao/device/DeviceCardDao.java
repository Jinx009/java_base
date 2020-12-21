package database.basicFunctions.dao.device;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.device.DeviceCard;
import utils.model.BaseConstant;

@Repository
public class DeviceCardDao extends BaseDao<DeviceCard>{

	public PageDataList<DeviceCard> getPage(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
