package database.dao;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.PuzhiJob;

@Repository
public class PuzhiJobDao extends BaseDao<PuzhiJob>{

	public PuzhiJob findByUuid(String uuid){
		QueryParam param = QueryParam.getInstance();
		param.addParam("featureUuid", uuid);
		return findByCriteriaForUnique(param);
	}
	
}
