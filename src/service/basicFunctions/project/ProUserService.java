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
	
	
	public ProUser getByOpenid(String openid){
		return proUserDao.getByOpenid(openid);
	}
	
	
	public void update(ProUser proUser){
		proUserDao.update(proUser);
	}
	
	

	public PageDataList<ProUser> findList(Integer p) {
		return proUserDao.list(p);
	}


	public void save(ProUser proUser) {
		proUserDao.save(proUser);
	}
	
}
