package service.basicFunctions.pro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProAppInfoDao;
import database.models.home.pro.ProAppInfo;

@Service
public class ProAppInfoService {

	@Autowired
	private ProAppInfoDao proAppInfoDao;
	
	public ProAppInfo save(ProAppInfo proAppInfo){
		return proAppInfoDao.save(proAppInfo);
	}
	
	public void update(ProAppInfo proAppInfo){
		proAppInfoDao.update(proAppInfo);
	}
	
	public ProAppInfo findByDomain(String domain){
		return proAppInfoDao.findByDomain(domain);
	}
	
}
