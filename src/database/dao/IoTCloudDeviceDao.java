package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.IoTCloudDevice;

@Repository
public class IoTCloudDeviceDao extends BaseDao<IoTCloudDevice>{

	public IoTCloudDevice findByImei(String imei) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("imei",imei);
		return findByCriteriaForUnique(queryParam);
	}

	public IoTCloudDevice findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac",mac);
		return findByCriteriaForUnique(queryParam);
	}

	public IoTCloudDevice findByDeviceId(String deviceId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("deviceId",deviceId);
		return findByCriteriaForUnique(queryParam);
	}

}
