package database.basicFunctions.dao.project;


import org.springframework.stereotype.Repository;

import common.helper.MD5Util;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProStudent;
import utils.BaseConstant;

@Repository
public class ProStudentDao extends BaseDao<ProStudent>{

	
	public PageDataList<ProStudent> homeList(Integer p,Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		if(type!=null&&type!=0){
			queryParam.addParam("type", type);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
	public ProStudent findByMobilePhone(String mobilePhone,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone",mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}
	
	
	public ProStudent login(String mobilePhone,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("pwd", MD5Util.md5(pwd));
		queryParam.addParam("mobilePhone",mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}
	
}
