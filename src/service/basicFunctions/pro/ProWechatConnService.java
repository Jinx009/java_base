package service.basicFunctions.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProWechatConnDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.pro.ProWechatConn;
import utils.BufferUtils;

@Service
public class ProWechatConnService {

	@Autowired
	private ProWechatConnDao proWechatConnDao;
	
	public List<ProWechatConn> findSuccess(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status", 1);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return proWechatConnDao.findByCriteria(queryParam);
	}

	public List<ProWechatConn> findUserLike(Integer userId) {
		String hql = BufferUtils.add(" FROM ProWechatConn WHERE first =  ",String.valueOf(userId)," AND status !=4 ");
		return proWechatConnDao.findUserLike(hql);
	}
	
}
