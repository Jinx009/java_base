package database.basicFunctions.dao.project;



import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.project.ProToken;

@Repository
public class ProTokenDao extends BaseDao<ProToken>{

	public ProToken getByAppId(String appId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("baseId",appId);
		queryParam.addOrder(OrderType.DESC, "createTime");
		List<ProToken> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
