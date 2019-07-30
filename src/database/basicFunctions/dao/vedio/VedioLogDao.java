package database.basicFunctions.dao.vedio;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.vedio.VedioLog;

@Repository
public class VedioLogDao extends BaseDao<VedioLog>{

	public List<VedioLog> findByTaskId(Integer taskId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("taskId", taskId);
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	public List<VedioLog> findById(Integer id) {
		VedioLog vedioLog = find(id);
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("taskId", vedioLog.getTaskId());
		queryParam.addParam("picNumber", vedioLog.getPicNumber());
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

}
