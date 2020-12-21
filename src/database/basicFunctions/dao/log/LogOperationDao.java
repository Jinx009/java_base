package database.basicFunctions.dao.log;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.log.LogOperation;

@Repository
public class LogOperationDao extends BaseDao<LogOperation>{

	@SuppressWarnings("unchecked")
	public List<LogOperation> findByMac(String mac) {
		String hql = " from LogOperation where mac like '%"+mac+"%' order by id desc ";
		List<LogOperation> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()) {
			return list;
		}
		return null;
	}

}
