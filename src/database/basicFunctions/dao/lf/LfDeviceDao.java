package database.basicFunctions.dao.lf;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.lf.LfDevice;

@Repository
public class LfDeviceDao extends BaseDao<LfDevice>{

	public LfDevice findById(String id) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("SensorID", id);
		return findByCriteriaForUnique(param);
	}

	
	
}
