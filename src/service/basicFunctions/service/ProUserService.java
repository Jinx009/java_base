package service.basicFunctions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProUserDao;
import database.models.project.ProUser;

@Service
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	public ProUser login(String mobilePhone,String pwd){
		return proUserDao.login(mobilePhone, pwd);
	}
	
}
