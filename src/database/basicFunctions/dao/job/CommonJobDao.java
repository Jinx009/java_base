package database.basicFunctions.dao.job;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.job.CommonJob;

@Repository
public class CommonJobDao extends BaseDao<CommonJob>{

	@SuppressWarnings("unchecked")
	public List<CommonJob> findNotFinish(String serviceName) {
		String hql = " FROM CommonJob Where( status = 1 or status = 0) AND serviceName =  '"+serviceName+"'";
		Query query = em.createQuery(hql);
		List<CommonJob> list = query.getResultList();
		if(!list.isEmpty()&&list!=null){
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CommonJob> findNotFinishAll() {
		String hql = " FROM CommonJob Where status = 1 or status = 0";
		Query query = em.createQuery(hql);
		List<CommonJob> list = query.getResultList();
		if(!list.isEmpty()&&list!=null){
			return list;
		}
		return null;
	}

	public List<CommonJob> findAllByServiceName(String serviceName) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("serviceName", serviceName);
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	
	
}
