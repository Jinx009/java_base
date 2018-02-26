package service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.WeixinParkUserDao;

@Service
public class WeixinParkUserService {

	@Autowired
	private WeixinParkUserDao weixinParkUserDao;
	
	
	
}
