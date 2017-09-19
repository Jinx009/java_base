package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.parking.ParkingPay;

@Repository
public class ParkingPayDao extends BaseDao<ParkingPay>{

	public List<ParkingPay> findAll(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"createTime");
		queryParam.addParam("showStatus",1);
		return findByCriteria(queryParam);
	}
	
}
