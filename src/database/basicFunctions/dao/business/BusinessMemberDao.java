package database.basicFunctions.dao.business;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.business.BusinessMember;

@Repository
public class BusinessMemberDao extends BaseDao<BusinessMember>{

	@SuppressWarnings("unchecked")
	public List<BusinessMember> findByDate(){
		String hql = " from BusinessMember where expiredTime = '2018-12-31 00:00:00' and recSt = 1  ";
		Query query = em.createQuery(hql);
		List<BusinessMember> list = query.getResultList();
		return list;
	}
	
}
