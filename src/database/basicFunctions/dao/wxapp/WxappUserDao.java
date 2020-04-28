package database.basicFunctions.dao.wxapp;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.wxapp.WxappUser;

@Repository
public class WxappUserDao extends BaseDao<WxappUser>{

	public WxappUser findByOpenid(String openid){
		QueryParam param = QueryParam.getInstance();
		param.addParam("openid", openid);
		return findByCriteriaForUnique(param);
	}
	
}
