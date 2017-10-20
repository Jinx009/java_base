package database.basicFunctions.dao.pro;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.pro.ProQRCode;

@Repository
public class ProQRCodeDao extends BaseDao<ProQRCode>{

	public ProQRCode getByKey(String key) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("keyword",key);
		return findByCriteriaForUnique(queryParam);
	}

	public List<ProQRCode> findAllByTime() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"createTime");
		return findByCriteria(queryParam);
	}
	
}
