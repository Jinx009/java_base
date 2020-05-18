package database.basicFunctions.dao.project;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProUser;
import utils.BaseConstant;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser getByOpenid(String openid){
		QueryParam param = QueryParam.getInstance();
		param.addParam("openid", openid);
		return findByCriteriaForUnique(param);
	}
	

	public PageDataList<ProUser> list(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
