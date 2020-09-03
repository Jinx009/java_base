package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.AliParking;

@Repository
public class AliParkingDao extends BaseDao<AliParking>{

	public PageDataList<AliParking> list() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(1, 200);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}
