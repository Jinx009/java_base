package service.basicFunctions.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.StringUtil;
import database.basicFunctions.dao.project.ProOrderDao;
import database.common.PageDataList;
import database.models.project.ProOrder;

@Service
public class ProOrderService {

	@Autowired
	private ProOrderDao proOrderDao;

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
	
	

	
	
}
