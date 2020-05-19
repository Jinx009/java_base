package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProOrder;
import utils.BaseConstant;

@Repository
public class ProOrderDao extends BaseDao<ProOrder>{

	public PageDataList<ProOrder> findPage(String openid, Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("openid", openid);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<ProOrder> findByDateTimeType(String date, String time, String type) {
		QueryParam qpParam = QueryParam.getInstance();
		qpParam.addParam("type", type);
		qpParam.addParam("date", date);
		qpParam.addParam("time", time);
		return findByCriteria(qpParam);
	}

	public PageDataList<ProOrder> findPageH(Integer fromSite, String fromDate, String toDate,Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
//		if(0!=fromSite){
//			queryParam.addParam("fromSite", fromSite);
//		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	
	
}
