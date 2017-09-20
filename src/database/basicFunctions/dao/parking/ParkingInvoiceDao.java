package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.parking.ParkingInvoice;

@Repository
public class ParkingInvoiceDao extends BaseDao<ParkingInvoice>{

	public List<ParkingInvoice> findAll(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"createTime");
		queryParam.addParam("showStatus",1);
		return findByCriteria(queryParam);
	}
	
}
