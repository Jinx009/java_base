package database.basicFunctions.dao.home;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.home.HomeResource;

@Repository
public class HomeResourceDao extends BaseDao<HomeResource>{

	public List<HomeResource> getMenu(Integer homeUserId){
		String sql = " SELECT a.* FROM HOME_RESOURCE a WHERE a.ID in( SELECT ID FROM HOME_RESOURCE_ROLE WHERE ROLR_ID = (SELECT ROLE_ID FROM HOME_USER_ROLE WHERE USER_ID = ? AND STATUS = 1 ) ) AND a.STATUS = 1 ";
		String[] keys = new String[]{"USER_ID"};
		Object[] values = new Object[]{homeUserId};
		return listBySql(sql, keys, values);
	}
	
}
