package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.model.GnssRtkLog;

@Repository
public class GnssRtkLogDao extends BaseDao<GnssRtkLog>{

	public PageDataList<GnssRtkLog> pages(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,20);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}
