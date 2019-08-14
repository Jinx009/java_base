package database.dao;

import java.util.List;

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

	public PageDataList<IoTCloudDevice> findAll(Integer p, Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",type);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<IoTCloudDevice> findByLocalIp(String localIp) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("localIp", localIp);
		return findByCriteria(param);
	}


	
}