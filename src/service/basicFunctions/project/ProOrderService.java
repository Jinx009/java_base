package service.basicFunctions.project;


import java.util.List;

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

	public List<ProOrder> findByDateTimeType(String date, String time, String type) {
		return proOrderDao.findByDateTimeType(date,time,type);
	}

	public PageDataList<ProOrder> findPage(Integer fromSite, String fromDate, String toDate,Integer p) {
		return proOrderDao.findPageH(fromSite, fromDate,toDate,p);
	}
	
}
