package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.model.GnssRtkControl;

@Repository
public class GnssRtkControlDao extends BaseDao<GnssRtkControl>{

	public List<GnssRtkControl> findByMacAndStatus(String mac, int status) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		queryParam.addParam("status", status);
		return findByCriteria(queryParam);
	}

	public Object findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(mac)) {
			queryParam.addParam("mac", mac);
		}
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	
	
}
