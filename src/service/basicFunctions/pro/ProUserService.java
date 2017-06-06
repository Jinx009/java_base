package service.basicFunctions.pro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProUserDao;
import database.models.home.pro.ProUser;

@Service
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	public ProUser login(String userName,String pwd){
		return proUserDao.login(userName, pwd);
	}
	
	public ProUser checkExits(String userName){
		return proUserDao.checkExits(userName);
	}
	
	public void update(ProUser proUser){
		proUserDao.update(proUser);
	}
	
	public ProUser save(ProUser proUser){
		return proUserDao.save(proUser);
	}

	public ProUser findByDomain(String domain) {
		return proUserDao.findByDomain(domain);
	}
	
}
