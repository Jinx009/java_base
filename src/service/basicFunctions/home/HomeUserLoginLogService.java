package service.basicFunctions.home;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.home.HomeUserLoginLogDao;
import database.models.home.HomeUserLoginLog;

@Service
public class HomeUserLoginLogService {

	@Autowired
	private HomeUserLoginLogDao homeUserLoginLogDao;
	
	public List<HomeUserLoginLog> find(){
		return homeUserLoginLogDao.find();
	}
	
	
	public void save(String userName,String realName){
		HomeUserLoginLog homeUserLoginLog = new HomeUserLoginLog();
		homeUserLoginLog.setCreateTime(new Date());
		homeUserLoginLog.setRealName(realName);
		homeUserLoginLog.setUserName(userName);
		homeUserLoginLogDao.save(homeUserLoginLog);
	}
	
}
