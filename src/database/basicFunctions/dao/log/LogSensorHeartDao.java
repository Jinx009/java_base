package database.basicFunctions.dao.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.log.LogSensorHeart;

@Repository
public class LogSensorHeartDao extends BaseDao<LogSensorHeart>{

	@SuppressWarnings("unchecked")
	public List<String> findAlive(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String sql = " select distinct mac from tbl_sensor_devicelog where create_time>'"+sdf.format(date)+"' order by mac  ";
		Query query = em.createNativeQuery(sql);
		List<Object> list = query.getResultList();
		if(list!=null){
			List<String> list_ = new ArrayList<String>();
			for(Object obj : list){
				list_.add(obj.toString());
			}
			return list_;
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<LogSensorHeart> findByMacAndDate(String mac, String dateStr) {
		String hql = " FROM LogSensorHeart  "+
		"where createTime>='"+dateStr+" 00:00:00' and createTime<='"+dateStr+" 23:59:59' and mac like'%"+mac+"%' and  dif is not null order by id desc  ";
		Query query = em.createQuery(hql);
		List<LogSensorHeart> list = query.getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}
	
}
