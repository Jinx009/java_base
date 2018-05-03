package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.IotCloudLog;
import utils.BaseConstant;
import utils.StringUtil;

@Repository
public class IotCloudLogDao extends BaseDao<IotCloudLog>{

	public PageDataList<IotCloudLog> findAll(Integer p, Integer type, String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",type);
		if(StringUtil.isNotBlank(mac)){
			queryParam.addParam("mac",mac);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
