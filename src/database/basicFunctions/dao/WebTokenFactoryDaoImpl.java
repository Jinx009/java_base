package database.basicFunctions.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.WebTokenFactory;

@Repository("webTokenFactoryDao")
public class WebTokenFactoryDaoImpl extends BaseDaoImpl<WebTokenFactory> implements WebTokenFactoryDao{

	@SuppressWarnings("unchecked")
	public List<WebTokenFactory> getByHql(String hql) {
		Query query = em.createQuery(hql);
		List<WebTokenFactory> list = query.getResultList();
		if(null!=list&&!list.isEmpty()){
			return list;
		}
		return null;
	}

}
