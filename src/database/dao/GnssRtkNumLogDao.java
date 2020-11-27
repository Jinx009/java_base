package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.model.GnssRtkNumLog;

@Repository
public class GnssRtkNumLogDao extends BaseDao<GnssRtkNumLog>{

	public GnssRtkNumLog find(String date, String mac, int startHour) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("date", date);
		param.addParam("mac", mac);
		param.addParam("startHour", startHour);
		return findByCriteriaForUnique(param);
	}

	@SuppressWarnings("unchecked")
	public List<GnssRtkNumLog> findByMac(String mac, String start, String end) {
		String hql = " from GnssRtkNumLog where date>='"+start+"' and date<='"+end+"' and mac='"+mac+"' order by date,startHour ";
		List<GnssRtkNumLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()) {
			return list;
		}
		return null;
	}

}
