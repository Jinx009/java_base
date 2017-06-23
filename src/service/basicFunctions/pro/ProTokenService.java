package service.basicFunctions.pro;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProTokenDao;
import database.models.pro.ProToken;

@Service
public class ProTokenService {

	@Autowired
	private ProTokenDao proTokenDao;
	
	public ProToken getByAppId(String appId){
		return proTokenDao.getByAppId(appId);
	}
	
	public void update(ProToken token){
		proTokenDao.update(token);
	}
	
	public ProToken save(ProToken token){
		return proTokenDao.save(token);
	}

	public ProToken createNew(String appId, ProToken token) {
		token.setBaseId(appId);
		token.setCreateTime(new Date());
		return save(token);
	}
	
}
