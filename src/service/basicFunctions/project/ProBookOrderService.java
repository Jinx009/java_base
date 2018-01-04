package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProBookOrderDao;
import database.common.PageDataList;
import database.models.project.ProBookOrder;

@Service
public class ProBookOrderService {

	@Autowired
	private ProBookOrderDao proBookOrderDao;
	
	public List<ProBookOrder> findByMobilePhone(String mobilePhone){
		return proBookOrderDao.findByMbilePhone(mobilePhone);
	}
	
	public PageDataList<ProBookOrder> homeList(Integer p){
		return proBookOrderDao.homeList(p);
	}

	public void updateStatus(Integer id, String postNum) {
		ProBookOrder proBookOrder = proBookOrderDao.find(id);
		proBookOrder.setStatus(1);
		proBookOrder.setPostNum(postNum);
		proBookOrder.setPostTime(new Date());
		proBookOrderDao.update(proBookOrder);
	}
	
}
