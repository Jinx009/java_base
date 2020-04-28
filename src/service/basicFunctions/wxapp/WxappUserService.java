package service.basicFunctions.wxapp;


import database.basicFunctions.dao.wxapp.WxappUserDao;
import database.models.wxapp.WxappUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxappUserService {

	@Autowired
	private WxappUserDao wxappUserDao;
	
	public WxappUser findByOpenid(String openid){
		return wxappUserDao.findByOpenid(openid);
	}
	
	public void save(WxappUser wxappUser){
		wxappUserDao.save(wxappUser);
	}
	
	public void update(WxappUser wxappUser){
		wxappUserDao.update(wxappUser);
	}
	
}
