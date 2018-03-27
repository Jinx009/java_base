package database.basicFunctions.dao.project;


import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.models.project.ProOrder;
import utils.BaseConstant;

@Repository
public class ProOrderDao extends  BaseDao<ProOrder>{

	public PageDataList<ProOrder> homeList(Integer p,Integer type,String orderDate){
		QueryParam queryParam = QueryParam.getInstance();
		if(type!=null&&type!=0){
			queryParam.addParam("type",type);
		}
		if(StringUtil.isNotBlank(orderDate)){
			queryParam.addParam("orderDate",orderDate);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public ProOrder findByUserId(String userId, String date, String orderTime, Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", type);
		queryParam.addParam("userId", userId);
		queryParam.addParam("orderDate", date);
		return findByCriteriaForUnique(queryParam);
	}
	
	public Integer findPoolStatus(String date, String orderTime) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", 2);
		queryParam.addParam("status", 0);
		queryParam.addParam("orderDate", date);
		List<ProOrder> list = findByCriteria(queryParam);
		int num = 0;
		for(ProOrder proOrder : list){
			num += proOrder.getNum();
		}
		if(num>15){
			return 0;
		}
		return 1;
	}
	
	public Integer findRoomStatus( String date, String orderTime) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", 1);
		queryParam.addParam("status", 0);
		queryParam.addParam("orderDate", date);
		List<ProOrder> list = findByCriteria(queryParam);
		if(list!=null&&list.size()>=3){
			return 0;
		}
		return 1;
	}
	
}
