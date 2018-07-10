package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProDriver;
import utils.BaseConstant;

@Repository
public class ProDriverDao extends BaseDao<ProDriver>{

	public PageDataList<ProDriver> homeList(Integer p,String name,String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(mobilePhone)){
			queryParam.addParam("mobilePhone", mobilePhone);
		}
		if(StringUtil.isNotBlank(name)){
			queryParam.addParam("name", name);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public ProDriver login(String mobilePhone, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addParam("pwd", pwd);
		return findByCriteriaForUnique(queryParam);
	}

	public ProDriver findByMobilePhone(String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}

	public List<ProDriver> selectList() {
		return findAll();
	}
	
}
