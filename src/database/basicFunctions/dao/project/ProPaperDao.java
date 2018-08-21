package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.project.ProPaper;

@Repository
public class ProPaperDao extends BaseDao<ProPaper>{


	public List<ProPaper> findByUserId(Integer userId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userId", userId);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findByCriteria(queryParam);
	}

	public List<ProPaper> selectList() {
		return findAll();
	}
	
}
