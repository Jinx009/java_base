package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.StringUtil;
import database.common.PageDataList;
import database.dao.GnssRtkErrLogDao;
import database.model.GnssRtkErrLog;

@Service
public class GnssRtkErrLogService {

	@Autowired
	private GnssRtkErrLogDao gnssRtkErrLogDao;
	
	public void save(GnssRtkErrLog gnssRtkErrLog) {
		gnssRtkErrLogDao.save(gnssRtkErrLog);
	}

	public PageDataList<GnssRtkErrLog> findByPageAndMac(int p, String mac) {
		return gnssRtkErrLogDao.findByPageAndMac(p, mac);
	}

	public void saveErrlog(String payload, String mac) {
		GnssRtkErrLog log = new GnssRtkErrLog();
		log.setBaseData(payload);
		log.setCreateTime(new Date());
		log.setMac(mac);
		log.setErrorContent(StringUtil.convertHexToString(payload));
		gnssRtkErrLogDao.save(log);
	}
	
}
