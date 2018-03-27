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

	public Integer getStatus(String date, Integer type, String userId,String orderTime) {
		if(StringUtil.isNotBlank(userId)){
			ProOrder proOrder = proOrderDao.findByUserId(userId,date,orderTime,type);
			if(proOrder!=null)
				return 2;
		}else{
			if(type==1){
				return proOrderDao.findRoomStatus(date, orderTime);
			}else{
				return proOrderDao.findPoolStatus(date, orderTime);
			}
		}
		return 1;
	}

	public void save(String orderDate, Integer type, Integer userId, Integer orderType, Integer num) {
		String orderTime = "上午";
		if(orderType == 2){
			orderTime = "下午";
		}
		if(orderType==3){
			orderTime = "夜间";
		}
		String userType = "游客";
		ProUser proUser = proUserDao.find(userId);
		if(proUser.getType()==1){
			userType = "游客";
		}
		if(proUser.getType()==2){
			userType = "教练";
		}
		if(proUser.getType()==3){
			userType = "企业";
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
		proOrder.setUserType(userType);
		proOrderDao.save(proOrder);
	}

	public List<ProOrder> myOrder(Integer userId) {
		return proOrderDao.myOrder(userId);
	}
	
	

	
	
}
