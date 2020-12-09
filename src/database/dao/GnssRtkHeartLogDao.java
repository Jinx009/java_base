package database.dao;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.model.GnssRtkHeartLog;

@Repository
public class GnssRtkHeartLogDao extends BaseDao<GnssRtkHeartLog>{

	public PageDataList<GnssRtkHeartLog> findByPage(Integer p, String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC, "id");
		if(StringUtil.isNotBlank(mac)) {
			param.addParam("mac", mac);
		}
		param.addPage(p,10);
		return findPageList(param);
	}

	
	
}
