package database.basicFunctions.dao.device;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.device.DeviceRepeater;

@Repository
public class DeviceRepeaterDao extends BaseDao<DeviceRepeater>{

	public DeviceRepeater findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac",mac);
		return findByCriteriaForUnique(queryParam);
	}

}
