package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.AccVersionLog;

@Repository
public class AccVersionLogDao extends BaseDao<AccVersionLog>{

	public AccVersionLog findByMac(String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		List<AccVersionLog> list = findByCriteria(param);
		if(list!=null&&!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public AccVersionLog findByMacAndVersion(String hard, String soft,String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		param.addParam("soft", soft);
		param.addParam("hard", hard);
		return findByCriteriaForUnique(param);
	}

}
