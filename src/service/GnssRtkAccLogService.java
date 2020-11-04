package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.StringUtil;
import database.common.PageDataList;
import database.dao.GnssRtkAccLogDao;
import database.model.GnssRtkAccLog;

@Service
public class GnssRtkAccLogService {

	@Autowired
	private GnssRtkAccLogDao gnssRtkAccLogDao;
	
	public void save(GnssRtkAccLog gnssRtkAccLog) {
		gnssRtkAccLogDao.save(gnssRtkAccLog);
	}

	public PageDataList<GnssRtkAccLog> findByPageAndMac(int p, String mac) {
		return gnssRtkAccLogDao.findByPageAndMac(p,mac);
	}

	public void saveAccLog(String payload, String mac) {
		GnssRtkAccLog log = new GnssRtkAccLog();
		double accX = StringUtil.hexToFloat(payload.substring(24,32));
		double accY = StringUtil.hexToFloat(payload.substring(32,40));
		double accZ = StringUtil.hexToFloat(payload.substring(40,48));
		double angleX = StringUtil.hexToFloat(payload.substring(48,56));
		double angleY = StringUtil.hexToFloat(payload.substring(56,64));
		double angleZ = StringUtil.hexToFloat(payload.substring(64,72));
		int flag = Integer.valueOf(payload.substring(18,20));
		int alarmtype = Integer.valueOf(payload.substring(20,22));
		String alarmvalue = StringUtil.getB(payload.substring(22, 24));
		double accXP2p = StringUtil.hexToFloat(payload.substring(72,80));
		double accYP2p = StringUtil.hexToFloat(payload.substring(80,88));
		double accZP2p = StringUtil.hexToFloat(payload.substring(88,96));
		double shockStrength = StringUtil.hexToFloat(payload.substring(96,104));
		log.setAccX(accX);
		log.setAccXP2p(accXP2p);
		log.setAccY(accY);
		log.setAccYP2p(accYP2p);
		log.setAccZ(accZ);
		log.setAccZP2p(accZP2p);
		log.setAlarmtype(alarmtype);
		log.setAlarmvalue(alarmvalue);
		log.setAngleX(angleX);
		log.setAngleY(angleY);
		log.setAngleZ(angleZ);
		log.setCreateTime(new Date());
		log.setFlag(flag);
		log.setShockStrength(shockStrength);
		gnssRtkAccLogDao.save(log);
	}
	
}
