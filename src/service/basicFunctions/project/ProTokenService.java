package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProTokenDao;
import database.models.project.ProToken;

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
		token.setTimestamp(token.getTimestamp());
		return save(token);
	}
	
	public ProToken getByToken(String token){
		return proTokenDao.getByToken(token);
	}
	
	public boolean checkToken(String token){
		ProToken proToken = proTokenDao.getByToken(token);
		if(proToken!=null){
			long now = new Date().getTime();
			if(now>proToken.getTimestamp()){
				return false;
			}
			return true;
		}
		return false;
	}
	
}
