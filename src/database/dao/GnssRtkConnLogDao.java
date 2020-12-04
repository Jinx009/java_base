package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.model.GnssRtkConnLog;

@Repository
public class GnssRtkConnLogDao extends BaseDao<GnssRtkConnLog>{

	public PageDataList<GnssRtkConnLog> findByMacAndP(int p, String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addPage(p, 10);
		param.addOrder(OrderType.DESC, "id");
		param.addParam("mac", mac);
		return findPageList(param);
	}

}
