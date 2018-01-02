package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import common.helper.MD5Util;
import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProUser;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser login(String mobilePhone, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addParam("pwd", MD5Util.md5(pwd));
		return findByCriteriaForUnique(queryParam);
	}

	
}
