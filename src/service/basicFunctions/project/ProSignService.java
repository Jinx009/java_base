package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProSignDao;
import database.models.project.ProSign;

@Service
public class ProSignService {

	@Autowired
	private ProSignDao proSignDao;
	
	public ProSign find(Integer id){
		return proSignDao.find(id);
	}
	
	public boolean save(List<ProSign> list){
		boolean res = false;
		for(int i = 0;i<list.size();i++){
			ProSign p = list.get(i);
			ProSign p2 = proSignDao.find(p.getId());
			if(p2==null&&i==(list.size()-1)){
				proSignDao.save(p);
				res = true;
			}else if(p2==null&&i!=(list.size()-1)){
				proSignDao.save(p);
			}
		}
		return res;
	}
	
	
}
