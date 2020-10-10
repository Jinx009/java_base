package database.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.AccAngleLog;

@Repository
public class AccAngleLogDao extends BaseDao<AccAngleLog>{

	public AccAngleLog findByMac(String mac) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		QueryParam param = QueryParam.getInstance();
		param.addParam("dateStr", dateStr);
		param.addParam("mac", mac);
		AccAngleLog log = findByCriteriaForUnique(param);
		if(log == null) {
			log = new AccAngleLog();
			log.setCreateTime(new Date());
			log.setUpdateTime(new Date());
			log.setDateStr(dateStr);
			log.setAccX(0.00);
			log.setAccY(0.00);
			log.setAccZ(0.00);
			log.setX(0.00);
			log.setY(0.00);
			log.setZ(0.00);
			log.setMac(mac);
			log = save(log);
		}
		return log;
	}

}
