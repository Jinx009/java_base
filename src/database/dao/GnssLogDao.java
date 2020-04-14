package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.model.GnssLog;

@Repository
public class GnssLogDao extends BaseDao<GnssLog>{

	public PageDataList<GnssLog> findByPage(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,200);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	@SuppressWarnings("unchecked")
	public List<GnssLog> getByDate(String startDate, String endDate,String mac) {
		String hql = " from GnssLog where  mac = '"+mac+"' and dateTime>='"+startDate+"' and dateTime <= '"+endDate+"'  and  dataType = 1";
		List<GnssLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

}
