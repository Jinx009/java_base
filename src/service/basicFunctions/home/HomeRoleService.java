package service.basicFunctions.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.home.HomeRoleDao;
import database.common.QueryParam;
import database.models.home.HomeRole;

@Service
public class HomeRoleService {

	@Autowired
	private HomeRoleDao homeRoleDao;
	
	public List<HomeRole> findAll(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",1);
		return homeRoleDao.findByCriteria(queryParam);
	}
	
}
