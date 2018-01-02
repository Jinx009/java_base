package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProBookPost;

@Repository
public class ProBookPostDao extends  BaseDao<ProBookPost>{

	public List<ProBookPost> personList(String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}

}
