package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.model.GnssDevice;

@Repository
public class GnssDeviceDao extends BaseDao<GnssDevice>{

	public GnssDevice findByMac(String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		return findByCriteriaForUnique(param);
	}
	
	
	
}
