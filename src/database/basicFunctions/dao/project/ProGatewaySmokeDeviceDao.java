package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProGatewaySmokeDevice;

@Repository
public class ProGatewaySmokeDeviceDao extends BaseDao<ProGatewaySmokeDevice>{

	public ProGatewaySmokeDevice findByMac(String mac){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}
	
}
