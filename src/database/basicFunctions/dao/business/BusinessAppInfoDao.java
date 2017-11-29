package database.basicFunctions.dao.business;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.business.BusinessAppInfo;
import utils.model.BaseConstant;

@Repository
public class BusinessAppInfoDao extends BaseDao<BusinessAppInfo>{

	public PageDataList<BusinessAppInfo> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
