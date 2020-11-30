package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.model.GnssRtkFirmware;

@Repository
public class GnssRtkFirmwareDao extends BaseDao<GnssRtkFirmware>{

	public PageDataList<GnssRtkFirmware> findByPage(int p) {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC, "id");
		param.addPage(p, 25);
		return findPageList(param);
	}

	
	
}
