package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
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

	public PageDataList<SocketConnLog> findByPage(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,200);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public SocketConnLog findBy(String ip, String clientPort, String connPort) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", "");
		queryParam.addParam("clientPort", clientPort);
		queryParam.addParam("ip", ip);
		queryParam.addParam("connPort", connPort);
		List<SocketConnLog> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
