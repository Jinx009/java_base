package database.basicFunctions.dao.home;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.home.HomeResource;

@Repository
public class HomeResourceDao extends BaseDao<HomeResource>{

	@SuppressWarnings("unchecked")
	public List<HomeResource> getMenu(Integer homeUserId){
		String sql = " SELECT a.* FROM home_resource a WHERE a.ID in( SELECT RESOURCE_ID FROM home_resource_role WHERE ROLE_ID = (SELECT ROLE_ID FROM home_user_role WHERE USER_ID = ? ) ) AND a.STATUS = 1 ";
		Query query = em.createNativeQuery(sql,HomeResource.class).setParameter(1,homeUserId);
		return query.getResultList();
	}

	public List<HomeResource> getPageResource() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",0);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}

	public List<HomeResource> getDataResource() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",1);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}
	
}
