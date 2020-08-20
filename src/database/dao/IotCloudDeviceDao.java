package database.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.IoTCloudDevice;
import utils.BaseConstant;
import utils.StringUtil;

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

	@SuppressWarnings("unchecked")
	public List<IoTCloudDevice> getWuhan() {
		String sql = " select *  from pro_device  where (local_ip ='QJ_ZHANWAY_V_3.0_WUHAN'  or local_ip ='QJ_ZHANWAY_V_3.1_WUHAN' )  order by park_name desc ";
		Query query = em.createNativeQuery(sql, IoTCloudDevice.class);
		List<IoTCloudDevice> list = query.getResultList();
		return list;
	}

	public List<IoTCloudDevice> findBySecret(String secret) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("secret", secret);
		param.addOrder(OrderType.DESC, "parkName");
		return findByCriteria(param);
	}

	@SuppressWarnings("unchecked")
	public List<IoTCloudDevice> getMap() {
		String sql = " select *  from pro_device  where (local_ip ='QJ_ZHANWAY_V_3.0_WUHAN'  or local_ip ='QJ_ZHANWAY_V_3.1_WUHAN' or local_ip='QJ_ZHANWAY_V_3.0_YIBIN'  or local_ip='QJ_ZHANWAY_V_3.0_GUANGDONG' )  order by park_name desc ";
		Query query = em.createNativeQuery(sql, IoTCloudDevice.class);
		List<IoTCloudDevice> list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<IoTCloudDevice> getByParkNameOrMac(String parkName) {
		String sql = " select *  from pro_device  where  park_name like '%"+parkName+"%' order by data_time ";
		if(StringUtil.isLetterDigit(parkName)){
			sql = " select *  from pro_device  where  mac like '%"+parkName+"%' order by data_time ";
		}
		Query query = em.createNativeQuery(sql, IoTCloudDevice.class);
		List<IoTCloudDevice> list = query.getResultList();
		return list;
	}


	
}
