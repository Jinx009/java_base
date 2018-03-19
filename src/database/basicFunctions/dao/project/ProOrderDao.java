package database.basicFunctions.dao.project;


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

	public PageDataList<ProOrder> homeList(Integer p,Integer type,String name,Integer status,String orderDate){
		QueryParam queryParam = QueryParam.getInstance();
		if(type!=null&&type!=0){
			queryParam.addParam("type",type);
		}
		if(status!=null&&status!=0){
			queryParam.addParam("status",status);
		}
		if(StringUtil.isNotBlank(orderDate)){
			queryParam.addParam("orderDate",orderDate);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
