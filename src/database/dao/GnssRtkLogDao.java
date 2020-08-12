package database.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.model.GnssRtkLog;

@Repository
public class GnssRtkLogDao extends BaseDao<GnssRtkLog>{

	public PageDataList<GnssRtkLog> pages(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,20);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	@SuppressWarnings("unchecked")
	public List<GnssRtkLog> findByRoverTagAndDate(String rovertag, String start, String end) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long starttime = sdf.parse(start).getTime();
		long endtime = sdf.parse(end).getTime();
		
		String hql = " from GnssRtkLog where rovertag = '"+rovertag+"' and updatetime>='"+starttime+"'  and updatetime <='"+endtime+"' ";
		System.out.println(hql);
		List<GnssRtkLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

}
