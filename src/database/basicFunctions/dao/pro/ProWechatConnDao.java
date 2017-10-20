package database.basicFunctions.dao.pro;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.pro.ProWechatConn;

@Repository
public class ProWechatConnDao extends BaseDao<ProWechatConn>{

	@SuppressWarnings("unchecked")
	public List<ProWechatConn> findByHql(String hql) {
		Query query = em.createQuery(hql);
		List<ProWechatConn> list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ProWechatConn> findUserLike(String hql) {
		Query query = em.createQuery(hql);
		List<ProWechatConn> list = query.getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

}
