package database.basicFunctions.dao.pro;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.web.pro.ProAccount;

@Repository
public class ProAccountDao extends BaseDao<ProAccount>{

	public ProAccount findByOpenid(String openid){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("openid",openid);
		return findByCriteriaForUnique(queryParam);
	}

	public List<ProAccount> findByAppId(Integer appId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("appId",appId);
		return findByCriteria(queryParam);
	}
	
}
