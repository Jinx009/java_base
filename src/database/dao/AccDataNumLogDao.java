package database.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.AccDataNumLog;

@Repository
public class AccDataNumLogDao extends BaseDao<AccDataNumLog>{

	@SuppressWarnings("static-access")
	public AccDataNumLog findByCurrent(String mac, String cmd) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		Calendar calendar = Calendar.getInstance();
		String hourStr = String.valueOf(calendar.get(calendar.HOUR_OF_DAY));
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		param.addParam("type", cmd);
		param.addParam("dateStr",dateStr);
		param.addParam("hourStr",hourStr);
		AccDataNumLog log = findByCriteriaForUnique(param);
		if(log == null) {
			log = new AccDataNumLog();
			log.setCreateTime(new Date());
			log.setDateStr(dateStr);
			log.setHourStr(hourStr);
			log.setMac(mac);
			log.setNum(0);
			log.setType(cmd);
			log = save(log);
		}
		return log;
	}
	
	public AccDataNumLog findByCurrentDate(String mac,String cmd) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		param.addParam("type", cmd);
		param.addParam("dateStr", dateStr);
		param.addParam("hourStr","-1");
		AccDataNumLog log = findByCriteriaForUnique(param);
		if(log == null) {
			log = new AccDataNumLog();
			log.setCreateTime(new Date());
			log.setDateStr(dateStr);
			log.setHourStr("-1");
			log.setMac(mac);
			log.setNum(0);
			log.setType(cmd);
			log = save(log);
		}
		return log;
	}
	


}
