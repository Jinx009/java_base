package database.basicFunctions.dao.device;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.device.DeviceErrorFlow;
import utils.model.BaseConstant;

@Repository
public class DeviceErrorFlowDao extends BaseDao<DeviceErrorFlow>{

	public PageDataList<DeviceErrorFlow> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findPageList(queryParam);
	}
	
}
