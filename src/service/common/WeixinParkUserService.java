package service.common;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.WeixinParkUserDao;
import database.models.WeixinParkUser;

@Service
public class WeixinParkUserService {

	@Autowired
	private WeixinParkUserDao weixinParkUserDao;
	
	public WeixinParkUser findByOpenid(String openid){
		return weixinParkUserDao.findByOpenid(openid);
	}
	
	public WeixinParkUser save(String openid){
		WeixinParkUser weixinParkUser = new WeixinParkUser();
		weixinParkUser.setCreateTime(new Date());
		weixinParkUser.setMobilePhone("");
		weixinParkUser.setOpenid(openid);
		weixinParkUser.setPlateNumber("");
		return weixinParkUserDao.save(weixinParkUser);
	}
	
	public void update(String mobilePhone,String plateNumber,Integer id){
		WeixinParkUser weixinParkUser = weixinParkUserDao.find(id);
		weixinParkUser.setMobilePhone(mobilePhone);
		weixinParkUser.setPlateNumber(plateNumber);
		weixinParkUserDao.update(weixinParkUser);
	}

	public WeixinParkUser save(WeixinParkUser user) {
		return weixinParkUserDao.save(user);
	}

	public void update(WeixinParkUser user) {
		weixinParkUserDao.update(user);
	}

	public WeixinParkUser findById(int id) {
		return weixinParkUserDao.find(id);
	}
	
}
