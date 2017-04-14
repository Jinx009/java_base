package database.basicFunctions.dao.home;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.HomeResource;

@Repository
public class HomeResourceDao extends BaseDao<HomeResource>{

	@SuppressWarnings("unchecked")
	public List<HomeResource> getMenu(Integer homeUserId){
		String sql = " SELECT a.* FROM HOME_RESOURCE a WHERE a.ID in( SELECT ID FROM HOME_RESOURCE_ROLE WHERE ROLE_ID = (SELECT ROLE_ID FROM HOME_USER_ROLE WHERE USER_ID = ? AND STATUS = 1 ) ) AND a.STATUS = 1 ";
		Query query = em.createNativeQuery(sql,HomeResource.class).setParameter(1,homeUserId);
		return query.getResultList();
	}

	public List<HomeResource> getPageResource() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",0);
		return findByCriteria(queryParam);
	}

	public List<HomeResource> getDataResource() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",1);
		return findByCriteria(queryParam);
	}
	
}
