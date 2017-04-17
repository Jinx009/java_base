package service.basicFunctions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.web.WebUserDao;
import database.models.web.WebUse;

@Service
public class WebUserService {

	@Autowired
	private WebUserDao webUserDao;
	
	public List<WebUse> findAll(){
		return webUserDao.findAll();
	}
	
}
