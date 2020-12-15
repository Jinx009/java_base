package database.basicFunctions.dao.log;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.log.LogSensorSource;

@Repository
public class LogSensorSourceDao extends BaseDao<LogSensorSource>{

	@SuppressWarnings("unchecked")
	public List<LogSensorSource> findByMacAndDate(String mac, String dateStr) {
		String hql = " FROM LogSensorSource  " + "where createTime>='" + dateStr + " 00:00:00' and createTime<='"
				+ dateStr + " 23:59:59'  and mac like'%" + mac + "%' order by id desc  ";
		Query query = em.createQuery(hql);
		List<LogSensorSource> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
}
