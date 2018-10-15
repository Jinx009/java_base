package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.parking.ParkingSensorInfo;

@Repository
public class ParkingSensorInfoDao extends BaseDao<ParkingSensorInfo>{

	public List<ParkingSensorInfo> findAllSensor() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"id");
		return findByCriteria(queryParam);
	}

}
