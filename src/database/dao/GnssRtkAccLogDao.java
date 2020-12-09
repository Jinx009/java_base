package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.model.GnssRtkAccLog;
import database.model.GnssRtkLog;

@Repository
public class GnssRtkAccLogDao extends BaseDao<GnssRtkAccLog>{

	public PageDataList<GnssRtkAccLog> findByPageAndMac(int p, String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addPage(p,10);
		param.addOrder(OrderType.DESC, "id");
		if(StringUtil.isNotBlank(mac)) {
			param.addParam("mac", mac);
		}
		return findPageList(param);
	}

	@SuppressWarnings("unchecked")
	public List<GnssRtkAccLog> list(String mac, String start, String end) {
		String hql = " from GnssRtkAccLog where mac = '"+mac+"' and createTime>='"+start+"'  and createTime <'"+end+"'  order by createTime ";
		List<GnssRtkAccLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}


}
