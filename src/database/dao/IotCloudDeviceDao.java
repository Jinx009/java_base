package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.IoTCloudDevice;
import utils.BaseConstant;

@Repository
public class IotCloudDeviceDao extends BaseDao<IoTCloudDevice>{

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

	public PageDataList<IoTCloudDevice> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	
}
