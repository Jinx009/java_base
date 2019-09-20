package database.basicFunctions.dao.wenshidu;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.wenshidu.WenshiduDevice;

@Repository
public class WenshiduDeviceDao extends BaseDao<WenshiduDevice> {

	public WenshiduDevice findByDeviceNumber(String deviceNumber){
		QueryParam param = QueryParam.getInstance();
		param.addParam("deviceNumber", deviceNumber);
		return findByCriteriaForUnique(param);
	}
	
}
