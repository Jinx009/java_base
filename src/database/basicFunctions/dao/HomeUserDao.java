package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.HomeUser;
import utils.MD5Util;

@Repository
public class HomeUserDao extends BaseDao<HomeUser>{

	public HomeUser login(String userName, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName", userName);
		queryParam.addParam("pwd", MD5Util.toMD5(pwd));
		return findByCriteriaForUnique(queryParam);
	}

}
