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

	public ProOrganization login(String mobilePhone, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addParam("pwd", MD5Util.md5(pwd));
		return findByCriteriaForUnique(queryParam);
	}

	public PageDataList<ProOrganization> homeList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public ProOrganization findByMobile(String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}

	public ProOrganization login_m(String mobilePhone, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addParam("pwd", pwd);
		return findByCriteriaForUnique(queryParam);
	}
	
}
