package database.basicFunctions.dao.parking;


import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.parking.ParkingNotice;

@Repository
public class ParkingNoticeDao extends BaseDao<ParkingNotice>{

	public List<ParkingNotice> findAll(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("showStatus",1);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}
	
}
