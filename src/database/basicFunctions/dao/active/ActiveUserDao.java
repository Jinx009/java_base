package database.basicFunctions.dao.active;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.QueryParam;
import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.models.active.ActiveUser;

@Repository
public class ActiveUserDao extends BaseDao<ActiveUser>{

	public List<ActiveUser> getByActiveId(Integer activeId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("activeId",activeId);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}

	public ActiveUser getByMobilePhone(String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone",mobilePhone);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteriaForUnique(queryParam);
	}
	
}
