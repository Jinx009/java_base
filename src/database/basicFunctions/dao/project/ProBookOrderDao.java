package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProBookOrder;
import utils.BaseConstant;

@Repository
public class ProBookOrderDao extends BaseDao<ProBookOrder>{

	public PageDataList<ProBookOrder> homeList(Integer p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
	public List<ProBookOrder> findByMbilePhone(String mobilePhone){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}
	
}
