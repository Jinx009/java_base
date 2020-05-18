package service.basicFunctions.project;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProOrderDao;
import database.common.PageDataList;
import database.models.project.ProOrder;

@Service
public class ProOrderService {

	@Autowired
	private ProOrderDao proOrderDao;
	
	public ProOrder saveOrder(ProOrder proOrder){
		return proOrderDao.save(proOrder);
	}
	
	public PageDataList<ProOrder> getByOpenid(String openid,Integer p){
		return proOrderDao.findPage(openid,p);
	}

	public ProOrder findById(Integer id) {
		return proOrderDao.find(id);
	}
	
	public void update(ProOrder proOrder) {
		proOrderDao.update(proOrder);
	}
	
}
