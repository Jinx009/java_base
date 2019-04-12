package service.basicFunctions.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
import common.helper.StringUtil;
import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProUser;

@Service
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	public ProUser login(String mobilePhone,String pwd){
		return proUserDao.login(mobilePhone, pwd);
	}
	
	public PageDataList<ProUser> homeList(Integer p,Integer type,String name){
		return proUserDao.homeList(p, type, name);
	}

	public void save(String mobilePhone, Integer type, String name, String desc, String remarkA, String remarkB,
			String remarkC, String pwd) {
		ProUser proUser = new ProUser();
		proUser.setCreateTime(new Date());
		proUser.setDesc(desc);
		proUser.setMobilePhone(mobilePhone);
		proUser.setName(name);
		proUser.setPwd(MD5Util.md5(pwd));
		proUser.setRemarkA(remarkA);
		proUser.setRemarkB(remarkB);
		proUser.setRemarkC(remarkC);
		proUser.setType(type);
		proUserDao.save(proUser);
	}

	public void resetPwd(Integer id) {
		ProUser proUser = proUserDao.find(id);
		proUser.setPwd(MD5Util.md5("123456"));
		proUserDao.update(proUser);
	}

	public void updateType(Integer id, Integer type) {
		ProUser proUser = proUserDao.find(id);
		proUser.setType(type);
		proUserDao.update(proUser);
	}

	public void updateRemark(Integer id, String desc, String remarkA) {
		ProUser proUser = proUserDao.find(id);
		proUser.setRemarkA(remarkA);
		proUser.setDesc(desc);
		proUserDao.update(proUser);
	}

	public ProUser findByMobilePhone(String userName) {
		return proUserDao.findByMobilePhone(userName);
	}

	public ProUser register(String userName, String pwd) {
		ProUser proUser = new ProUser();
		proUser.setCreateTime(new Date());
		proUser.setMobilePhone(userName);
		proUser.setName("");
		proUser.setPwd(MD5Util.md5(pwd));
		proUser.setType(1);
		
		return proUserDao.save(proUser);
	}

	public ProUser findById(Integer userId) {
		return proUserDao.find(userId);
	}

	public void updateN(Integer userId, String name, String pwd, String remarkB) {
		ProUser proUser = proUserDao.find(userId);
		proUser.setName(name);
		if(StringUtil.isNotBlank(pwd)){
			proUser.setPwd(MD5Util.md5(pwd));
		}
		proUser.setRemarkB(remarkB);
		proUserDao.update(proUser);
	}

	public ProUser register(String userName, String pwd, String remarkB, String name) {
		ProUser proUser = new ProUser();
		proUser.setCreateTime(new Date());
		proUser.setMobilePhone(userName);
		proUser.setName(name);
		proUser.setRemarkB(remarkB);
		proUser.setPwd(MD5Util.md5(pwd));
		proUser.setType(1);
		
		return proUserDao.save(proUser);
	}
	
}
