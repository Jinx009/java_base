package database.basicFunctions.dao.pro;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.pro.ProToken;

@Repository
public class ProTokenDao extends BaseDao<ProToken>{

	@SuppressWarnings("unchecked")
	public ProToken getByAppId(String appId){
		String hql = " FROM ProToken WHERE baseId= '"+appId+"' ORDER BY createTime DESC ";
		Query query = em.createQuery(hql);
		List<ProToken> list = query.getResultList();
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
