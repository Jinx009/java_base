package database.basicFunctions.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.device.DeviceSensorInfo;
import utils.StringUtil;

@Repository
public class DeviceSensorInfoDao extends BaseDao<DeviceSensorInfo>{

	@SuppressWarnings("unchecked")
	public List<DeviceSensorInfo> findByMacAndAddress(String mac, String address) {
		String hql = "  from DeviceSensorInfo where address = '"+address+"'  ";
		if(StringUtil.isNotBlank(mac)) {
			hql+= " and mac like '%"+mac+"%' ";
		}
		hql += " order by parkNumber";
		List<DeviceSensorInfo> list = em.createQuery(hql).getResultList();
		return list;
	}

	
	
}
