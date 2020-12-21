package database.basicFunctions.dao.business;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.business.BusinessLocation;
import utils.model.BaseConstant;

@Repository
public class BusinessLocationDao extends BaseDao<BusinessLocation>{

	public PageDataList<BusinessLocation> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addOrder(OrderType.DESC,"id");
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		return findPageList(queryParam);
	}
	
	public List<BusinessLocation> all(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt",1);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}

	public void create(BusinessLocation businessLocation) {
		businessLocation.setCreateTime(new Date());
		businessLocation.setRecSt(1);
		save(businessLocation);
	}
	
}
