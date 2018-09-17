package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProLog;
import utils.BaseConstant;

@Repository
public class ProLogDao extends BaseDao<ProLog>{

	public PageDataList<ProLog> homeList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC, "id");
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		return findPageList(queryParam);
	}
	
	
}
