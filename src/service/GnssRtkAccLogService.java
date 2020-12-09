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
		String b = getB(payload.substring(22, 24));
		log.setAlarmvalue(b);
		log.setFlag(Integer.valueOf(payload.substring(18,20)));
		log.setAlarmtype(Integer.valueOf(payload.substring(20, 22)));
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
	
	
	private String getB(String data) {
		Integer num = Integer.parseInt(data, 16);
		String a = Integer.toBinaryString(num);
		int[] s = new int[] { 0, 0, 0, 0, 0, 0 };
		if (a.length() == 1) {
			s[5] = Integer.valueOf(a);
		}
		if (a.length() == 2) {
			s[4] = Integer.valueOf(a.substring(0, 1));
			s[5] = Integer.valueOf(a.substring(1, 2));
		}
		if (a.length() == 3) {
			s[3] = Integer.valueOf(a.substring(0, 1));
			s[4] = Integer.valueOf(a.substring(1, 2));
			s[5] = Integer.valueOf(a.substring(2, 3));
		}
		if (a.length() == 4) {
			s[2] = Integer.valueOf(a.substring(0, 1));
			s[3] = Integer.valueOf(a.substring(1, 2));
			s[4] = Integer.valueOf(a.substring(2, 3));
			s[5] = Integer.valueOf(a.substring(3, 4));
		}
		if (a.length() == 5) {
			s[1] = Integer.valueOf(a.substring(0, 1));
			s[2] = Integer.valueOf(a.substring(1, 2));
			s[3] = Integer.valueOf(a.substring(2, 3));
			s[4] = Integer.valueOf(a.substring(3, 4));
			s[5] = Integer.valueOf(a.substring(4, 5));
		}
		if (a.length() == 6) {
			s[0] = Integer.valueOf(a.substring(0, 1));
			s[1] = Integer.valueOf(a.substring(1, 2));
			s[2] = Integer.valueOf(a.substring(2, 3));
			s[3] = Integer.valueOf(a.substring(3, 4));
			s[4] = Integer.valueOf(a.substring(4, 5));
			s[5] = Integer.valueOf(a.substring(5, 6));
		}
		String str = ""+s[5]+"_"+s[4]+"_"+s[3]+"_"+s[2]+"_"+s[1]+"_"+s[0];
		return str;
	}

	public PageDataList<GnssRtkAccLog> findByPage(Integer p, String mac) {
		return gnssRtkAccLogDao.findByPageAndMac(p, mac);
	}
	
}
