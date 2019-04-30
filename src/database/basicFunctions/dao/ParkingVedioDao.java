package database.basicFunctions.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.ParkingVedio;

@Repository
public class ParkingVedioDao extends BaseDao<ParkingVedio>{

	@SuppressWarnings("unchecked")
	public List<ParkingVedio> findByTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = new Date(date.getTime()-300000);
		String hql = "FROM ParkingVedio where changeTime < '"+sdf.format(date2)+"'";
		List<ParkingVedio> list = em.createQuery(hql).getResultList();
		return list;
	}
	
}
