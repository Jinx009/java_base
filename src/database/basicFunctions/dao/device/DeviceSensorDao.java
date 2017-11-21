package database.basicFunctions.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.device.DeviceSensor;

@Repository
public class DeviceSensorDao extends BaseDao<DeviceSensor>{

	public List<DeviceSensor> findUse(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findByCriteria(queryParam);
	}
	
}
