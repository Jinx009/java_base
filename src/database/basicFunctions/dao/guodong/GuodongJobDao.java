package database.basicFunctions.dao.guodong;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.guodong.GuodongJob;

@Repository
public class GuodongJobDao extends BaseDao<GuodongJob>{

	public GuodongJob findByDevEui(String devEui){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",0);
		queryParam.addParam("devEui",devEui);
		return findByCriteriaForUnique(queryParam);
	}

	public List<GuodongJob> findAllByTime() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"id");
		return findByCriteria(queryParam);
	}

	public GuodongJob findByTaskId(String taskID) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",0);
		queryParam.addParam("taskId",taskID);
		return  findByCriteriaForUnique(queryParam);
	}
	
}
