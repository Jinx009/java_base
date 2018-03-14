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
		queryParam.addParam("appid",appId);
		return findByCriteria(queryParam);
	}
	
}
