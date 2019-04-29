package database.basicFunctions.dao;


import java.util.List;

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
		List<ParkInfo> p = page.getList();
		if(p!=null&&!p.isEmpty()){
			return p.get(0).getBaseId();
		}
		return 0;
	}
	
}
