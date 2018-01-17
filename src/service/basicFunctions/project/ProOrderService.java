package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProOrderDao;
import database.models.project.ProOrder;

@Service
public class ProOrderService {

	@Autowired
	private ProOrderDao proOrderDao;
	
	public ProOrder find(Integer id){
		return proOrderDao.find(id);
	}
	
	public boolean save(List<ProOrder> list){
		boolean res = false;
		for(int i = 0;i<list.size();i++){
			ProOrder p = list.get(i);
			ProOrder p2 = proOrderDao.find(p.getId());
			if(p2==null&&i==(list.size()-1)){
				proOrderDao.save(p);
				res = true;
			}else if(p2==null&&i!=(list.size()-1)){
				proOrderDao.save(p);
			}
		}
		return res;
	}
	
}
