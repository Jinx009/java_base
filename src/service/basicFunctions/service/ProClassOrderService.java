package service.basicFunctions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProClassOrderDao;
import database.common.PageDataList;
import database.models.project.ProClassOrder;

@Service
public class ProClassOrderService {
	
	@Autowired
	private ProClassOrderDao proClassOrderDao;

	public PageDataList<ProClassOrder> homeList(String classDate,Integer p){
		return proClassOrderDao.homeList(p, classDate);
	}

	public void changeStatus(Integer id, Integer status) {
		ProClassOrder proClassOrder = proClassOrderDao.find(id);
		proClassOrder.setStatus(status);
		proClassOrderDao.update(proClassOrder);
	}

	public void saveRemark(Integer id, String remark) {
		ProClassOrder proClassOrder = proClassOrderDao.find(id);
		proClassOrder.setRemark(remark);
		proClassOrderDao.update(proClassOrder);
	}
	
}
