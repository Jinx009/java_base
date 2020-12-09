package service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import database.common.PageDataList;
import database.dao.GnssRtkHeartLogDao;
import database.model.GnssRtkHeartLog;

@Service
public class GnssRtkHeartLogService {

	@Autowired
	private GnssRtkHeartLogDao gnssRtkHeartLogDao;
	
	public void save(GnssRtkHeartLog gnssRtkHeartLog) {
		gnssRtkHeartLogDao.save(gnssRtkHeartLog);
	}

	public void saveHeartbeat(String payload, String mac) {
		GnssRtkHeartLog log = new GnssRtkHeartLog();
		log.setMacModel(convertHexToString(payload.substring(20,52)));//机型
		log.setHardEdition(convertHexToString(payload.substring(68, 80)));
		log.setSoftEdition(convertHexToString(payload.substring(80, 92)));
		log.setPlateType(convertHexToString(payload.substring(92, 104)));
		try {
			log.setVoltage(getData(payload.substring(104, 105),payload.substring(104, 108)));
			log.setTem(String.valueOf(Integer.parseInt(payload.substring(108, 110), 16)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String lng = getHex10(payload.substring(110, 118));
		String lat = getHex10(payload.substring(118,126));
		String height = getHex(payload.substring(126, 134));
		log.setLng(lng);
		log.setLat(lat);
		log.setHeight(height);
		log.setRssi(String.valueOf(Integer.parseInt(payload.substring(126, 128), 16)));
		log.setBer(String.valueOf(Integer.parseInt(payload.substring(128, 130), 16)));
		log.setBaseData(payload);
		log.setMac(mac);
		log.setCreateTime(new Date());
		gnssRtkHeartLogDao.save(log);
	}
	
	
	
	
	private String getHex(String str) {// 0DE0FE43-A7C52512
		String s1 = str.substring(6, 8);
		String s2 = str.substring(4, 6);
		String s3 = str.substring(2, 4);
		String s4 = str.substring(0, 2);
		String str2 = s1 + s2 + s3 + s4;
		return Integer.valueOf(str2, 16).toString();
	}
	
	
	private String getHex10(String str) {// 0DE0FE43-A7C52512-F8350000
		String s1 = str.substring(6, 8);
		String s2 = str.substring(4, 6);
		String s3 = str.substring(2, 4);
		String s4 = str.substring(0, 2);
		String str2 = s1 + s2 + s3 + s4;
		Integer i = Integer.valueOf(str2, 16);
		Double r = Double.valueOf(i);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue() / 10000000;
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}
	
	
	private String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}
	
	
	private String getData(String index, String _d) throws Exception {
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		Double r = Double.valueOf(e) / 1000;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}

	public PageDataList<GnssRtkHeartLog> findByPage(Integer p, String mac) {
		return gnssRtkHeartLogDao.findByPage(p,mac);
	}
	
}
