package service.basicFunctions.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.home.HomeUserDao;
import database.models.home.HomeUser;
import service.basicFunctions.BaseService;
import utils.MD5Util;

@Service
public class HomeUserService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(HomeUserService.class);

	@Autowired
	private HomeUserDao homeUserDao;
	
	public HomeUser login(String username,String password){
		try {
			return homeUserDao.login(username, MD5Util.toMD5(password));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
}
