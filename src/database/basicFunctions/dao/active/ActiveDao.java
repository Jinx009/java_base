package database.basicFunctions.dao.active;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.active.Active;

@Repository
public class ActiveDao extends BaseDao<Active>{

	public Active getByKey(String  key) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("keyword",key);
		return findByCriteriaForUnique(queryParam);
	}
	
}
