package database.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.PuzhiJob;

@Repository
public class PuzhiJobDao extends BaseDao<PuzhiJob>{

	public PuzhiJob findByUuid(String uuid){
		QueryParam param = QueryParam.getInstance();
		param.addParam("msgid", uuid);
		return findByCriteriaForUnique(param);
	}

	public int findByMacAndTaskStatus(String mac, int taskStatus) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		param.addParam("taskStatus", taskStatus);
		List<PuzhiJob>list = findByCriteria(param);
		if(list!=null&&!list.isEmpty()){
			return list.size();
		}
		return 0;
	}

	public PuzhiJob findByTelTaskId(String cammandId) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("telcomTaskId", cammandId);
		return findByCriteriaForUnique(param);
	}
	
}
