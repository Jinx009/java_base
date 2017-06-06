package database.basicFunctions.dao.pro;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.pro.ProUser;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser login(String userName,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName",userName);
		queryParam.addParam("pwd",pwd);
		return findByCriteriaForUnique(queryParam);
	}
	
	public ProUser checkExits(String userName){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName",userName);
		return findByCriteriaForUnique(queryParam);
	}

	public ProUser findByDomain(String domain) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("domain",domain);
		return findByCriteriaForUnique(queryParam);
	}
}
