package service.basicFunctions.home;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import database.basicFunctions.dao.home.HomeUserDao;
import database.common.QueryParam;
import database.models.home.HomeUser;
import database.models.home.vo.HomeUserVo;

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

	public List<HomeUserVo> getHomeUser() throws ParseException {
		return homeUserDao.getHomeUser();
	}
	
}
