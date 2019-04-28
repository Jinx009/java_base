package database.basicFunctions.dao;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.ParkInfo;

@Repository
public class ParkInfoDao extends BaseDao<ParkInfo>{

	public Integer getMaxBaseId(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(1,5);
		queryParam.addOrder(OrderType.DESC, "baseId");
		PageDataList<ParkInfo> page = 	findPageList(queryParam);	
		ParkInfo p = page.getList().get(0);
		return p.getBaseId();
	}
	
}
