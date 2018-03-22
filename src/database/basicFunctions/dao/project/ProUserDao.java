package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import common.helper.MD5Util;
import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter;
import database.common.SearchFilter.Operators;
import database.common.OrderFilter.OrderType;
import database.models.project.ProUser;
import utils.BaseConstant;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser login(String mobilePhone,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone",mobilePhone);
		queryParam.addParam("pwd",MD5Util.md5(pwd));
		return findByCriteriaForUnique(queryParam);
	}
	
	public ProUser findByMobilePhone(String mobilePhone){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone",mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}
	
	public PageDataList<ProUser> homeList(Integer p,Integer type,String name) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(name)){
			SearchFilter filter = new SearchFilter("name", Operators.LIKE, name);
			SearchFilter filter2 = new SearchFilter("name", Operators.LIKE, name);
			queryParam.addOrFilter(filter,filter2);
		}
		if(type!=null&&type!=0){
			queryParam.addParam("type",type);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
