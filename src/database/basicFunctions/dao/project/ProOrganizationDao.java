package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import common.helper.MD5Util;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProOrganization;
import utils.BaseConstant;

@Repository
public class ProOrganizationDao extends BaseDao<ProOrganization>{

	public ProOrganization login(String userName, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName", userName);
		queryParam.addParam("pwd", MD5Util.md5(pwd));
		return findByCriteriaForUnique(queryParam);
	}
	
	public ProOrganization findByUserName(String userName) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName", userName);
		return findByCriteriaForUnique(queryParam);
	}

	public PageDataList<ProOrganization> homeList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	
}
