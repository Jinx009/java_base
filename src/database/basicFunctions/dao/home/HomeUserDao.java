package database.basicFunctions.dao.home;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.HomeUser;

@Repository
public class HomeUserDao extends BaseDao<HomeUser>{

	public HomeUser login(String username,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("username", username);
		queryParam.addParam("password", pwd);
		return findByCriteriaForUnique(queryParam);
	}
	
}
