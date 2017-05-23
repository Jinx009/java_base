package database.basicFunctions.dao.home;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.HomeUserRole;

@Repository
public class HomeUserRoleDao extends BaseDao<HomeUserRole>{

	public HomeUserRole findByUserId(Integer userId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userId",userId);
		return findByCriteriaForUnique(queryParam);
	}

}
