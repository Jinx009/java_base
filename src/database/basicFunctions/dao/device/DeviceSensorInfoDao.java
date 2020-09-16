package database.basicFunctions.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.device.DeviceSensorInfo;
import utils.StringUtil;

@Repository
public class DeviceSensorInfoDao extends BaseDao<DeviceSensorInfo>{

	@SuppressWarnings("unchecked")
	public List<DeviceSensorInfo> findByMacAndAddress(String mac, String address) {
		String hql = "  from DeviceSensorInfo where address = '"+address+"'  ";
		if(StringUtil.isNotBlank(mac)) {
			hql = "  from DeviceSensorInfo where mac like '%"+mac+"%'  ";;
		}
		hql += " order by parkNumber";
		List<DeviceSensorInfo> list = em.createQuery(hql).getResultList();
		return list;
	}

	public List<DeviceSensorInfo> findAllDevice() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.ASC, "parkNumber");
		return findByCriteria(queryParam);
	}

	@SuppressWarnings("unchecked")
	public List<String> findByAddressLike(String address) {
		String sql = "select distinct(address) from tbl_sensor_info  where address like '%"+address+"%'";
		List<String> list = em.createNativeQuery(sql,String.class).getResultList();
		return list;
	}

	
	
}
