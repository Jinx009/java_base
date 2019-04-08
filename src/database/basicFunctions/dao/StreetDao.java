package database.basicFunctions.dao;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.Street;
import utils.model.BaseConstant;

public class StreetDao extends BaseDao<Street>{

	public PageDataList<Street> findByPage(int p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}
