package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.parking.ParkingMonthCard;

@Repository
public class ParkingMonthCardDao extends BaseDao<ParkingMonthCard>{

	public List<ParkingMonthCard> findAll(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"createTime");
		queryParam.addParam("showStatus", 1);
		return findByCriteria(queryParam);
	}

	public boolean findByCardCode(String code) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("showStatus", 1);
		queryParam.addParam("cardNo", code);
		List<ParkingMonthCard> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
	}

	public List<ParkingMonthCard> findAll(Integer status, Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"createTime");
		queryParam.addParam("showStatus", 1);
		if(3!=status){
			queryParam.addParam("status", status);
		}
		if(0!=type){
			queryParam.addParam("type", type);
		}
		return findByCriteria(queryParam);
	}
	
}
