package database.basicFunctions.dao.pro;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.pro.ProAppInfo;

@Repository
public class ProAppInfoDao extends BaseDao<ProAppInfo>{

	public ProAppInfo findByDomain(String domain) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("domain",domain);
		return findByCriteriaForUnique(queryParam);
	}

}
