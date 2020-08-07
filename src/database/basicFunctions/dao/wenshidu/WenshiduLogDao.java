package database.basicFunctions.dao.wenshidu;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.wenshidu.WenshiduLog;

@Repository
public class WenshiduLogDao extends BaseDao<WenshiduLog>{

	@SuppressWarnings("unchecked")
	public WenshiduLog findByDateAndNum(String deviceNumber) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String hql = " from WenshiduLog where  deviceNumber='"+deviceNumber+"' and  time >'"+date+" 00:00:00'  order by id desc";
		Query query = em.createQuery(hql);
		List<WenshiduLog> list = query.getResultList();
		if(!list.isEmpty()&&list!=null){
			return list.get(0);
		}
		return null;
	}

	
	
}
