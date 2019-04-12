package service.basicFunctions.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.StringUtil;
import database.basicFunctions.dao.project.ProOrderDao;
import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProOrder;
import database.models.project.ProUser;

@Service
public class ProOrderService {

	@Autowired
	private ProOrderDao proOrderDao;
	@Autowired
	private ProUserDao proUserDao;

	public PageDataList<ProOrder> homeList(String classDate, Integer p, Integer type) {
		return proOrderDao.homeList(p, type, classDate);
	}

	public void saveRemark(Integer id, String remark, Integer realNum) {
		ProOrder proOrder = proOrderDao.find(id);
		proOrder.setRealNum(realNum);
		proOrder.setRemark(remark);
		proOrderDao.update(proOrder);
	}

	public Integer getStatus(String date, Integer type, String userId, String orderTime) {
		if (StringUtil.isNotBlank(userId)) {
			ProOrder proOrder = proOrderDao.findByUserId(userId, date, orderTime, type);
			if (proOrder != null)
				return 1000;
		}
		if (type == 1) {
			return proOrderDao.findDivingStatus(date, orderTime);
		} else if (type == 2) {
			return proOrderDao.findPoolStatus(date, orderTime);
		} else {
			return proOrderDao.findRoomStatus(date, orderTime);
		}
	}

	public void save(String orderDate, Integer type, Integer userId, Integer userType, Integer num) {
		String orderTime = "上午";
		if (type == 2) {
			orderTime = "下午";
		}
		if (type == 3) {
			orderTime = "夜间";
		}
		String _userType = "普通账户";
		ProUser proUser = proUserDao.find(userId);
		if (proUser.getType() == 1) {
			_userType = "潜水教练";
		}
		if (proUser.getType() == 2) {
			_userType = "游泳教练";
		}
		if (proUser.getType() == 3) {
			_userType = "水肺潜水会员";
		}
		if (proUser.getType() == 4) {
			_userType = "自由潜会员";
		}
		if (proUser.getType() == 5) {
			_userType = "未分级账号";
		}

		ProOrder proOrder = new ProOrder();
		proOrder.setCreateTime(new Date());
		proOrder.setMobilePhone(proUser.getMobilePhone());
		proOrder.setMoney(0.00);
		proOrder.setName(proUser.getName());
		proOrder.setNum(num);
		proOrder.setOrderDate(orderDate);
		proOrder.setOrderTime(orderTime);
		proOrder.setRealNum(0);
		proOrder.setStatus(0);
		proOrder.setType(type);
		proOrder.setUserId(userId);
		proOrder.setUserType(_userType);
		proOrderDao.save(proOrder);
	}

	public List<ProOrder> myOrder(Integer userId) {
		return proOrderDao.myOrder(userId);
	}

	public List<ProOrder> findOrder(Integer userId, String orderDate, String orderTime, int type) {
		return proOrderDao.findOrder(userId, orderDate, orderTime, type);
	}

	public List<ProOrder> findOrder(String orderDate, String orderTime, int type) {
		return proOrderDao.findOrder(orderDate, orderTime, type);
	}

	public List<ProOrder> findOrderByTime(String dateStr, String time) {
		return proOrderDao.findOrderByTime(dateStr, time);
	}

}
