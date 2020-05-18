package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProPrice;

@Repository
public class ProPriceDao extends BaseDao<ProPrice>{

	public List<ProPrice> findOrderLevel() {
		QueryParam qpParam = QueryParam.getInstance();
		qpParam.addOrder(OrderType.ASC, "level");
		return findByCriteria(qpParam);
	}
	

}
