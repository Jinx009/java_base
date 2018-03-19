package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProClass;
import utils.BaseConstant;

@Repository
public class ProClassDao extends BaseDao<ProClass>{

	public List<ProClass> frontList(String classDate) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("classDate", classDate);
		queryParam.addOrder(OrderType.DESC,"id");
		return findByCriteria(queryParam);
	}
	

	public PageDataList<ProClass> homeList(Integer p,String classDate,Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(classDate)){
			queryParam.addParam("classDate",classDate);
		}
		if(type!=null&&type!=0){
			queryParam.addParam("type",type);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
