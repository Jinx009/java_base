package service.basicFunctions.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProDao;
import database.models.home.pro.Pro;

@Service
public class ProService {

	@Autowired
	private ProDao proDao;
	
	public List<Pro> findByAppId(Integer appId){
		return proDao.findByAppId(appId);
	}
	
	public List<Pro> findAll(){
		return proDao.findAll();
	}
	
	public void update(Pro pro){
		proDao.update(pro);
	}
	
	public Pro save(Pro pro){
		return proDao.save(pro);
	}
	
}
