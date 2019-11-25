package database.basicFunctions.dao.project;


import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProVedio;
import utils.BaseConstant;

@Repository
public class ProVedioDao extends BaseDao<ProVedio>{

	public PageDataList<ProVedio> findByLevel(Integer level,Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addParam("vedioLevel", level);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	
	
}
