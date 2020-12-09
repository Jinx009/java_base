package service;

import java.util.Date;
import java.util.List;

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
		int flag = Integer.valueOf(payload.substring(16,18));
		int alarmtype = Integer.valueOf(payload.substring(18,20));
		String alarmvalue = StringUtil.getB(payload.substring(20, 22));
		double accX = StringUtil.hexToFloat(payload.substring(22,30));
		double accY = StringUtil.hexToFloat(payload.substring(30,38));
		double accZ = StringUtil.hexToFloat(payload.substring(38,46));
		double angleX = StringUtil.hexToFloat(payload.substring(46,54));
		double angleY = StringUtil.hexToFloat(payload.substring(54,62));
		double angleZ = StringUtil.hexToFloat(payload.substring(62,70));
		double accXP2p = StringUtil.hexToFloat(payload.substring(70,78));
		double accYP2p = StringUtil.hexToFloat(payload.substring(78,86));
		double accZP2p = StringUtil.hexToFloat(payload.substring(86,94));
		double shockStrengthX = StringUtil.hexToFloat(payload.substring(94,102));
		double shockStrengthY = StringUtil.hexToFloat(payload.substring(102,110));
		double shockStrengthZ = StringUtil.hexToFloat(payload.substring(110,118));
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
		log.setMac(mac);
		log.setShockStrengthX(shockStrengthX);
		log.setShockStrengthY(shockStrengthY);
		log.setShockStrengthZ(shockStrengthZ);
		gnssRtkAccLogDao.save(log);
	}
	

	public PageDataList<GnssRtkAccLog> findByPage(Integer p, String mac) {
		return gnssRtkAccLogDao.findByPageAndMac(p, mac);
	}

	public List<GnssRtkAccLog> list(String mac, String start, String end) {
		return gnssRtkAccLogDao.list(mac, start, end);
	}
	
	
	
	
}
