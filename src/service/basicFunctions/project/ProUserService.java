package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProUser;

@Service
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	public void updateLevel(Integer id){
		proUserDao.setLevel(id);
	}
	
	public ProUser login(String userName,String pwd){
		return proUserDao.getByUserNameAndPwd(userName, pwd);
	}
	
	public ProUser register(String userName,String pwd){
		return proUserDao.register(userName, pwd);
	}
	
	public ProUser findByUserName(String userName){
		return proUserDao.findByUserName(userName);
	}

	public PageDataList<ProUser> findList(Integer p) {
		return proUserDao.list(p);
	}
	
}
