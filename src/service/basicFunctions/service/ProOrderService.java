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
				return 1000;
		}else{
			if(type==1){
				return proOrderDao.findDivingStatus(date, orderTime);
			}else if(type==2){
				return proOrderDao.findPoolStatus(date, orderTime);
			}else{
				return proOrderDao.findRoomStatus(date, orderTime);
			}
		}
		return 1;
	}

	public void save(String orderDate, Integer type, Integer userId, Integer orderType, Integer num) {
		String orderTime = "上午";
		if(type!=2){
			if(orderType == 2){
				orderTime = "下午";
			}
			if(orderType==3){
				orderTime = "夜间";
			}
		}else{
			if(orderType == 1){
				orderTime = "09:00:00~10:30:00";
			}
			if(orderType==2){
				orderTime = "10:30:00~12:00:00";
			}
			if(orderType == 3){
				orderTime = "12:00:00~13:30:00";
			}
			if(orderType==4){
				orderTime = "13:30:00~15:00:00";
			}
			if(orderType == 5){
				orderTime = "15:00:00~16:30:00";
			}
			if(orderType==6){
				orderTime = "16:30:00~18:00:00";
			}
			if(orderType == 7){
				orderTime = "18:00:00~19:30:00";
			}
			if(orderType==8){
				orderTime = "19:30:00~21:00:00";
			}
		}
		String userType = "普通账户";
		ProUser proUser = proUserDao.find(userId);
		if(proUser.getType()==1){
			userType = "普通账户";
		}
		if(proUser.getType()==2){
			userType = "会员";
		}
		if(proUser.getType()==3){
			userType = "俱乐部";
		}
		if(proUser.getType()==4){
			userType = "游泳教练";
		}
		if(proUser.getType()==5){
			userType = "潜水教练";
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
