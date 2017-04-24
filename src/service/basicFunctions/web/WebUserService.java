package service.basicFunctions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.web.WebUserDao;
import database.models.web.WebUser;

@Service
public class WebUserService {

	@Autowired
	private WebUserDao webUserDao;
	
	public List<WebUser> findAll(){
		return webUserDao.findAll();
	}

	public void changeStatus(Integer status, Integer id) {
		WebUser webUser = webUserDao.find(id);
		if(webUser!=null){
			webUser.setStatus(status);
			webUserDao.update(webUser);
		}
	}
	
}
