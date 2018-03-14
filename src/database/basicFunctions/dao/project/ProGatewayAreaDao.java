package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProGatewayArea;

@Repository
public class ProGatewayAreaDao extends BaseDao<ProGatewayArea>{

	public ProGatewayArea getByAreaId(Integer areaId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("areaId",areaId);
		return findByCriteriaForUnique(queryParam);
	}
	
}
