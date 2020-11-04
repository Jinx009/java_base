package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.StringUtil;
import database.dao.GnssRtkDebugLogDao;
import database.model.GnssRtkDebugLog;

@Service
public class GnssRtkDebugLogService {

	@Autowired
	private GnssRtkDebugLogDao gnssRtkDebugLogDao;
	
	public void save(GnssRtkDebugLog log) {
		gnssRtkDebugLogDao.save(log);
	}

	public void saveDebuglog(String payload, String mac) {
		GnssRtkDebugLog log = new GnssRtkDebugLog();
		log.setBaseData(payload);
		log.setCreateTime(new Date());
		log.setDebugContent(StringUtil.convertHexToString(payload));
		log.setMac(mac);
		gnssRtkDebugLogDao.save(log);
	}
	
}
