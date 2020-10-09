package service.basicFunctions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccDataLogDao;
import database.models.AccDataLog;

@Service
public class AccDataLogService {

	@Autowired
	private AccDataLogDao accDataLogDao;
	
	private static final String HEART = "心跳";
	private static final String WARN = "报警";
	
	public void save(String data,Integer type) {
		try {
			int dataType = 0;
			if(type==3) {
				String mac = data.substring(0, 16);
				String cmd = data.substring(20, 22);
				if("68".equals(cmd)) {
					String acc_x = hexToFloat(data.substring(26, 34));
					String acc_y = hexToFloat(data.substring(34, 42));
					String acc_z = hexToFloat(data.substring(42, 50));
					String x = getData100(data.substring(50, 51), data.substring(50, 54));
					String y = getData100(data.substring(54, 55), data.substring(54, 58));
					String z = getData100(data.substring(58, 59), data.substring(58, 62));
					String bat = getData(data.substring(62, 63), data.substring(62, 66));
					String tem = String.valueOf(Integer.parseInt(data.substring(66, 68), 16));
					String rssi = String.valueOf(Integer.parseInt(data.substring(68, 70), 16));
					String rsrp = String.valueOf(Double.valueOf(getDataBase(data.substring(70, 71), data.substring(70, 74))));
					String snr = String.valueOf(Double.valueOf(getDataBase(data.substring(74, 75), data.substring(74, 78))));
					String pci = String.valueOf(Double.valueOf(getDataBase(data.substring(78, 79), data.substring(78, 82))));
					String hard = convertHexToString(data.substring(82, 94));
					String soft = convertHexToString(data.substring(94, 106));
					AccDataLog log = new AccDataLog();
					log.setBaseAcceX(acc_x);
					log.setBaseAcceY(acc_y);
					log.setBaseAcceZ(acc_z);
					log.setBaseX(x);
					log.setBaseY(y);
					log.setBaseZ(z);
					log.setVoltage(bat);
					log.setTem(tem);
					log.setRssi(rssi);
					log.setRsrp(rsrp);
					log.setSnr(snr);
					log.setHardV(hard);
					log.setSoftV(soft);
					log.setPci(pci);
					log.setType(HEART);
					log.setMac(mac);
					log.setDataType(dataType);
					log.setCreateTime(new Date());
					accDataLogDao.save(log);
				}
				if("69".equals(cmd)) {
					AccDataLog log = new AccDataLog();
					int[] arr = getB(data.substring(28, 30));
					log.setAcceXType(arr[5]);
					log.setAcceYType(arr[4]);
					log.setAcceZType(arr[3]);
					log.setXType(arr[2]);
					log.setYType(arr[1]);
					log.setZType(arr[0]);
					String acc_x = hexToFloat(data.substring(30, 38));
					String acc_y = hexToFloat(data.substring(38, 46));
					String acc_z = hexToFloat(data.substring(46, 54));
					String x = getData100(data.substring(54, 55), data.substring(54, 58));
					String y = getData100(data.substring(58, 59), data.substring(58, 62));
					String z = getData100(data.substring(62, 63), data.substring(62, 66));
					String acc_x_max = hexToFloat(data.substring(66, 74));
					String acc_y_max = hexToFloat(data.substring(74, 82));
					String acc_z_max = hexToFloat(data.substring(82, 90));
					String acc_x_min = hexToFloat(data.substring(90, 98));
					String acc_y_min = hexToFloat(data.substring(98, 106));
					String acc_z_min = hexToFloat(data.substring(106, 114));
					String bat = getData(data.substring(114, 115), data.substring(114, 118));
					String tem = String.valueOf(Integer.parseInt(data.substring(118, 120), 16));
					log.setType(cmd);
					log.setBaseAcceX(acc_x);
					log.setBaseAcceY(acc_y);
					log.setBaseAcceZ(acc_z);
					log.setBaseX(x);
					log.setBaseY(y);
					log.setBaseZ(z);
					log.setCreateTime(new Date());
					log.setMac(mac);
					log.setType(WARN);
					log.setTem(tem);
					log.setAccxMax(acc_x_max);
					log.setAccyMax(acc_y_max);
					log.setAcczMax(acc_z_max);
					log.setAccxMin(acc_x_min);
					log.setAccyMin(acc_y_min);
					log.setAcczMin(acc_z_min);
					log.setVoltage(bat);
					log.setDataType(dataType);
					log.setCreateTime(new Date());
					accDataLogDao.save(log);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveChange(String mac, String cmd, String x, String y, String z, String acc_x, String acc_y,String acc_z) {
		AccDataLog log = new AccDataLog();
		log.setDataType(1);
		log.setMac(mac);
		log.setType(cmd);
		log.setBaseAcceX(acc_x);
		log.setBaseAcceY(acc_y);
		log.setBaseAcceZ(acc_z);
		log.setBaseX(x);
		log.setBaseY(y);
		log.setBaseZ(z);
		log.setCreateTime(new Date());
		accDataLogDao.save(log);
	}
	
	
	/**
	 * 16进制字符串IEEE754标准转小数
	 * 
	 * @param s
	 * @return
	 */
	private static String hexToFloat(String s) {
		BigInteger big = new BigInteger(s, 16);
		Float z = Float.intBitsToFloat(big.intValue());
		Double r = Double.valueOf(z);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}

	private String getData100(String index, String _d) throws Exception {
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
		Double r = Double.valueOf(e) / 100;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
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
	
	private String getDataBase(String index, String _d) throws Exception {
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
		Double r = Double.valueOf(e);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}

	public String convertHexToString(String hex) {
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
	
	
	public int[] getB(String data) {
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
		return s;
	}



}
