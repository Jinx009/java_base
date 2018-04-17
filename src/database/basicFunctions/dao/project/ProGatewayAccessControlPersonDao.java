package database.basicFunctions.dao.project;

import java.util.List;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.project.ProGatewayAccessControlPerson;

@Repository
public class ProGatewayAccessControlPersonDao extends BaseDao<ProGatewayAccessControlPerson>{

	public ProGatewayAccessControlPerson findNear(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.ASC,"updateTime");
		List<ProGatewayAccessControlPerson> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
