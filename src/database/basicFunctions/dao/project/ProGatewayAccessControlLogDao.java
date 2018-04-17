package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProGatewayAccessControlLog;

@Repository
public class ProGatewayAccessControlLogDao extends BaseDao<ProGatewayAccessControlLog>{

	public PageDataList<ProGatewayAccessControlLog> pageList(Integer p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, 25);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
