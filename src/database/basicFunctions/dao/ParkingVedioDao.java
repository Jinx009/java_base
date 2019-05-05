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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date2 = new Date(date.getTime()-300000);
		String hql = "FROM ParkingVedio where eventTime < '"+sdf.format(date2)+" ' and sendStatus = 0";
		List<ParkingVedio> list = em.createQuery(hql).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ParkingVedio> findByStatus() {
		String hql = "FROM ParkingVedio where sendStatus =1  and updateStatus = 0";
		List<ParkingVedio> list = em.createQuery(hql).getResultList();
		return list;
	}
	
}
