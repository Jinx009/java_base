package database.basicFunctions.dao.business;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.business.BusinessArea;
import utils.model.BaseConstant;

@Repository
public class BusinessAreaDao extends BaseDao<BusinessArea>{

	public PageDataList<BusinessArea> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<BusinessArea> all(Integer locationId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("locationId", locationId);
		queryParam.addParam("recSt",1);
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}

	public void create(BusinessArea businessArea) {
		businessArea.setCreateTime(new Date());
		businessArea.setRecSt(1);
		save(businessArea);
	}
	
}
