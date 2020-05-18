package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProOrder;
import utils.BaseConstant;

@Repository
public class ProOrderDao extends BaseDao<ProOrder>{

	public PageDataList<ProOrder> findPage(String openid, Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("openid", openid);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	
	
}
