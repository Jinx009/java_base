package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.model.GnssLog;

@Repository
public class GnssLogDao extends BaseDao<GnssLog>{

	public PageDataList<GnssLog> findByPage(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,50);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}
