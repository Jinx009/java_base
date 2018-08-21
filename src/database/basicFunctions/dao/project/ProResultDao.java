package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.project.ProResult;

@Repository
public class ProResultDao extends BaseDao<ProResult>{

	public List<ProResult> findByPaperId(Integer paperId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("paperId", paperId);
		queryParam.addOrder(OrderType.ASC,"createTime");
		return findByCriteria(queryParam);
	}
	
}
