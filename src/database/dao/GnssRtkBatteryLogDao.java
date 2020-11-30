package database.dao;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.model.GnssRtkBatteryLog;

@Repository
public class GnssRtkBatteryLogDao extends BaseDao<GnssRtkBatteryLog>{

	public PageDataList<GnssRtkBatteryLog> findByPage(int p,String imei) {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC, "id");
		if(StringUtil.isNotBlank(imei)) {
			param.addParam("imei", imei);
		}
		param.addPage(p, 25);
		return findPageList(param);
	}

}
