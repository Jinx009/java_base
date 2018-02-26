package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.WeixinParkUser;

@Repository
public class WeixinParkUserDao extends BaseDao<WeixinParkUser>{

	public WeixinParkUser findByOpenid(String openid){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("openid", openid);
		return findByCriteriaForUnique(queryParam);
	}
	
	
}
