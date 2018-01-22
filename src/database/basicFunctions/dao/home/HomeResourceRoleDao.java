package database.basicFunctions.dao.home;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.home.HomeResourceRole;

@Repository
public class HomeResourceRoleDao extends BaseDao<HomeResourceRole>{

	public List<HomeResourceRole> findByRole(Integer roleId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("roleId", roleId);
		return findByCriteria(queryParam);
	}
	
}
