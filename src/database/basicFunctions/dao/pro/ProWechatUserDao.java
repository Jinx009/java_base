package database.basicFunctions.dao.pro;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.pro.ProWechatUser;

@Repository
public class ProWechatUserDao extends BaseDao<ProWechatUser>{

	@SuppressWarnings("unchecked")
	public List<ProWechatUser> findByHql(String hql){
		Query query = em.createQuery(hql);
		List<ProWechatUser> list = query.getResultList();
		return list;
	}
	
}
