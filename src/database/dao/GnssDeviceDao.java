package database.dao;

import java.util.List;

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

	public List<GnssDevice> openDeviceData() {
		QueryParam param = QueryParam.getInstance();
		param.addParam("status", 1);
		return findByCriteria(param);
	}
	
	
	
}
