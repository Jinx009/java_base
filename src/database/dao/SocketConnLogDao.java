package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.model.SocketConnLog;

@Repository
public class SocketConnLogDao extends BaseDao<SocketConnLog>{

	public List<SocketConnLog> findAll2() {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC, "id");
		return findByCriteria(param);
	}

}
