package service.basicFunctions.active;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.active.ActiveDao;
import database.models.active.Active;

@Service
public class ActiveService {

	@Autowired
	private ActiveDao activeDao;
	
	public void save(Active active){
		activeDao.save(active);
	}
	
	public List<Active> findAll(){
		return activeDao.findAll();
	}
	
	public void delete(Integer id){
		activeDao.delete(id);
	}
	
	public Active getByKey(String key){
		return activeDao.getByKey(key);
	}
	
}
