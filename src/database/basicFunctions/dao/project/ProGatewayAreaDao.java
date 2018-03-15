package database.basicFunctions.dao.project;

import java.util.List;

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

	public List<ProGatewayArea> findByLocationId(Integer locationId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("locationId",locationId);
		return findByCriteria(queryParam);
	}
	
}
