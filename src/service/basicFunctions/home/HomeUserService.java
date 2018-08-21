package service.basicFunctions.home;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
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
		queryParam.addParam("pwd",MD5Util.md5(pwd));
		return homeUserDao.findByCriteriaForUnique(queryParam);
	}

	public HomeUser findByUserName(String userName){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName",userName);
		return homeUserDao.findByCriteriaForUnique(queryParam);
	}
	
	public void register(String userName,String pwd,String realName){
		HomeUser homeUser = new HomeUser();
		homeUser.setCreateTime(new Date());
		homeUser.setPwd(MD5Util.md5(pwd));
		homeUser.setRealName(realName);
		homeUser.setUserName(userName);
		homeUserDao.save(homeUser);
	}

	
}
