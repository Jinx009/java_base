package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.models.project.ProUser;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser findByCard(String card){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("card",card);
		queryParam.addParam("status",1);
		return findByCriteriaForUnique(queryParam);
	}

	public List<ProUser> findAllUse() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",1);
		return findByCriteria(queryParam);
	}

	public PageDataList<ProUser> pageList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, 25);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
