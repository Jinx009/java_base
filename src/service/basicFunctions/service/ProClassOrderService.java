package service.basicFunctions.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProClassDao;
import database.basicFunctions.dao.project.ProClassOrderDao;
import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProClass;
import database.models.project.ProClassOrder;
import database.models.project.ProUser;

@Service
public class ProClassOrderService {
	
	@Autowired
	private ProClassOrderDao proClassOrderDao;
	@Autowired
	private ProUserDao proUserDao;
	@Autowired
	private ProClassDao proClassDao;

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

	public void save(Integer userId, Integer classId) {
		ProUser proUser = proUserDao.find(userId);
		ProClass proClass = proClassDao.find(classId);
		ProClassOrder proClassOrder = new ProClassOrder();
		proClassOrder.setClassDate(proClass.getClassDate());
		proClassOrder.setClassId(String.valueOf(classId));
		proClassOrder.setClassName(proClass.getName());
		proClassOrder.setClassTime(proClass.getTime());
		proClassOrder.setCreateTime(new Date());
		proClassOrder.setMobilePhone(proUser.getMobilePhone());
		proClassOrder.setName(proUser.getName());
		proClassOrder.setRemark("");
		proClassOrder.setStatus(0);
		proClassOrder.setUserId(userId);
		proClassOrderDao.save(proClassOrder);
	}

	public List<ProClassOrder> list(Integer userId) {
		return proClassOrderDao.list(userId);
	}
	
}
