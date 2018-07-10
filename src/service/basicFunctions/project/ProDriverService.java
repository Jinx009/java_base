package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
import database.basicFunctions.dao.project.ProDriverDao;
import database.common.PageDataList;
import database.models.project.ProDriver;

@Service
public class ProDriverService {

	@Autowired
	private ProDriverDao proDriverDao;
	
	public void save(ProDriver proDriver){
		proDriverDao.save(proDriver);
	}
	
	public void update(ProDriver proDriver){
		proDriverDao.update(proDriver);
	}
	
	public PageDataList<ProDriver> homeList(Integer p,String name,String mobilePhone){
		return proDriverDao.homeList(p, name, mobilePhone);
	}

	public ProDriver login(String mobilePhone, String pwd) {
		pwd = MD5Util.md5(pwd);
		return proDriverDao.login(mobilePhone,pwd);
	}
	
	public ProDriver login_m(String mobilePhone, String pwd) {
		return proDriverDao.login(mobilePhone,pwd);
	}

	public ProDriver findByMobilePhone(String mobilePhone) {
		return proDriverDao.findByMobilePhone(mobilePhone);
	}
}
