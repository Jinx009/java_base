package database.basicFunctions.dao.pro;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.pro.Pro;

@Repository
public class ProDao extends BaseDao<Pro>{

	public List<Pro> findByAppId(Integer appId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("appId",appId);
		return findByCriteria(queryParam);
	}
	
}
