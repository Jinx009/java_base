package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.model.GnssRtkNumLog;

@Repository
public class GnssRtkNumLogDao extends BaseDao<GnssRtkNumLog>{

	public GnssRtkNumLog find(String date, String mac, int startHour) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("date", date);
		param.addParam("mac", mac);
		param.addParam("startHour", startHour);
		return findByCriteriaForUnique(param);
	}

}
