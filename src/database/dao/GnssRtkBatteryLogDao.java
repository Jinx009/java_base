package database.dao;

import java.text.SimpleDateFormat;
import java.util.List;

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

	@SuppressWarnings("unchecked")
	public List<GnssRtkBatteryLog> all(String imei, String start, String end) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long starttime = sdf.parse(start).getTime();
		long endtime = sdf.parse(end).getTime();
		String hql = " from GnssRtkBatteryLog where imei = '"+imei+"' and timestamp>='"+starttime+"'  and timestamp <='"+endtime+"' ";
		List<GnssRtkBatteryLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

}
