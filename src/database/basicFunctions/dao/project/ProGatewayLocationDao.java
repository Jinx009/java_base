package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProGatewayLocation;

@Repository
public class ProGatewayLocationDao extends BaseDao<ProGatewayLocation>{

	public List<ProGatewayLocation> getByAppId(String appId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("appId",appId);
		return findByCriteria(queryParam);
	}

	public ProGatewayLocation getByLocationId(Integer locationId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("locationId",locationId);
		List<ProGatewayLocation> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
