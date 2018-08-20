package service.basicFunctions.subcribe;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
import database.basicFunctions.dao.subcribe.SubcribeUserDao;
import database.models.subcribe.SubcribeUser;

@Service
public class SubcribeUserService {

	@Autowired
	private SubcribeUserDao subcribeUserDao;
	
	public void save(String mobilePhone,String pwd,String plateNumber,String name){
		SubcribeUser subcribeUser = new SubcribeUser();
		subcribeUser.setCreateTime(new Date());
		subcribeUser.setMobilePhone(mobilePhone);
		subcribeUser.setName(name);
		subcribeUser.setPlateNumber(plateNumber);
		subcribeUser.setPwd(MD5Util.md5(pwd));
		subcribeUserDao.save(subcribeUser);
	}
	
	public SubcribeUser login(String mobilePhone,String pwd){
		return subcribeUserDao.login(mobilePhone,MD5Util.md5(pwd));
	}
	
	public SubcribeUser findByMobilePhone(String mobilePhone){
		return subcribeUserDao.findByMobilePhone(mobilePhone);
	}
	
}
