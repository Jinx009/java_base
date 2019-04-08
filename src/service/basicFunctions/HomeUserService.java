package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.HomeUserDao;
import database.models.HomeUser;

@Service
public class HomeUserService {

	@Autowired
	private HomeUserDao homeUserDao;
	
	
	public HomeUser login(String userName,String pwd){
		return homeUserDao.login(userName,pwd);
	}
	
}
