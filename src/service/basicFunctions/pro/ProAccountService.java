package service.basicFunctions.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProAccountDao;
import database.models.web.pro.ProAccount;

@Service
public class ProAccountService {

	@Autowired
	private ProAccountDao proAccountDao;
	
	public ProAccount findByOpenid(String openid){
		return proAccountDao.findByOpenid(openid);
	}
	
	public ProAccount save(ProAccount proAccount){
		return proAccountDao.save(proAccount);
	}
	
	public void update(ProAccount proAccount){
		proAccountDao.update(proAccount);
	}
	
	public List<ProAccount> findByAppId(Integer appId){
		return proAccountDao.findByAppId(appId);
	}
	
}
