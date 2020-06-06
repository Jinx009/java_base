package database.basicFunctions.dao.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProGoods;

@Repository
public class ProGoodsDao extends BaseDao<ProGoods>{

	public List<ProGoods> findByDate(String date) {
		QueryParam qpParam = QueryParam.getInstance();
		qpParam.addParam("date", date);
		qpParam.addOrder(OrderType.ASC, "id");
		return findByCriteria(qpParam);
	}


	public List<ProGoods> findByDateTime(String date, String s) {
		QueryParam qpParam = QueryParam.getInstance();
		qpParam.addParam("date", date);
		qpParam.addParam("time", s);
		return findByCriteria(qpParam);
	}
	
	public ProGoods findByDateTimeName(String date, String s,String name) {
		QueryParam qpParam = QueryParam.getInstance();
		qpParam.addParam("date", date);
		qpParam.addParam("time", s);
		qpParam.addParam("name", name);
		return findByCriteriaForUnique(qpParam);
	}


	public ProGoods findByDateTimeAbc(String date, String time, String string) {
		QueryParam qpParam = QueryParam.getInstance();
		qpParam.addParam("date", date);
		qpParam.addParam("time", time);
		qpParam.addParam("abc", string);
		return findByCriteriaForUnique(qpParam);
	}


	@SuppressWarnings("unchecked")
	public List<ProGoods> findByDateUpdate(String time, String name) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hql = " from ProGoods where date>='"+sdf.format(date)+"' and time='"+time+"' and name='"+name+"' ";
		List<ProGoods> list  = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	
	

}
