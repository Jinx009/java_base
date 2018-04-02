package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.IotCloudLog;
import utils.BaseConstant;

@Repository
public class IotCloudLogDao extends BaseDao<IotCloudLog>{

	public PageDataList<IotCloudLog> findAll(Integer p, Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",type);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
