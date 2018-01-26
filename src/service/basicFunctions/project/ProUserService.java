package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProUser;

@Service
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	public ProUser saveNew(String mobilePhone,String pwd,String realName,String address){
		ProUser proUser = new ProUser();
		proUser.setAddress(address);
		proUser.setCreateTime(new Date());
		proUser.setCurrentPoints(0);
		proUser.setMobilePhone(mobilePhone);
		proUser.setNickName("");
		proUser.setOpenid("");
		proUser.setPwd(MD5Util.md5(pwd));
		proUser.setRealName(realName);
		return proUserDao.save(proUser);
	}
	
	public ProUser login(String mobilePhone,String pwd){
		return proUserDao.login(mobilePhone,pwd);
	}
	
	public ProUser findByMobilePhone(String mobilePhone){
		return proUserDao.findByMobile(mobilePhone);
	}
	
	public  void update(ProUser proUser){
		proUserDao.update(proUser);
	}

	public PageDataList<ProUser> all(Integer p) {
		return proUserDao.homeList(p);
	}

	public ProUser login_m(String mobilePhone, String pwd) {
		return proUserDao.login_m(mobilePhone,pwd);
	}
	
}
