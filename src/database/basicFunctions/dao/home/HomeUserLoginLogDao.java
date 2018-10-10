package database.basicFunctions.dao.home;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.home.HomeUserLoginLog;

@Repository
public class HomeUserLoginLogDao extends BaseDao<HomeUserLoginLog>{

	public List<HomeUserLoginLog> find(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}
	
	
	
}
