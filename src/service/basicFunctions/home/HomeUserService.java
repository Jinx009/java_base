package service.basicFunctions.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import database.basicFunctions.dao.home.HomeUserDao;
import database.common.QueryParam;
import database.models.home.HomeUser;

@Service
public class HomeUserService {

	@Autowired
	private HomeUserDao homeUserDao;
	
	public HomeUser login(String userName,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName",userName);
		queryParam.addParam("pwd",pwd);
		queryParam.addParam("status",1);
		return homeUserDao.findByCriteriaForUnique(queryParam);
	}
	
}
