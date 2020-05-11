package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Test {

	private static double EARTH_RADIUS = 6378.137;// 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static String getLog(File fileName) {
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
			BufferedReader br = new BufferedReader(reader);
			StringBuilder result = new StringBuilder();
			String s = null;
			List<String> list = new ArrayList<String>();
			while ((s = br.readLine()) != null) {
				list.add(System.lineSeparator() + s);
			}
			br.close();
			if (list != null && !list.isEmpty()) {
				for (int i = list.size() - 1; i >= 0; i--) {
					result.append(list.get(i));
				}
			}
			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDataBase(String index, String _d) throws Exception {
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

		return String.valueOf(e);
	}

	public static void main(String[] args) {
		// try {
		// String data = "0001180614000002488e3d0b00080108002c001b2a2f00";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date = new Date();
		// File f = new
		// File("/apps/logs/sensor_status/"+sdf.format(date)+".txt");
		// File fileParent = f.getParentFile();
		// if (!fileParent.exists()) {
		// fileParent.mkdirs();
		// f.createNewFile();
		// }
		// FileWriter fw = new FileWriter(f, true);
		// PrintWriter pw = new PrintWriter(fw);
		// pw.println(data+" "+sdf1.format(date));
		// pw.flush();
		// fw.flush();
		// pw.close();
		// fw.close();
		// String av = data.substring(24, 26);
		// Integer avalable = Integer.valueOf(av);
		// String mode = data.substring(26, 28);
		// String dif1 = data.substring(32, 34);
		// String dif2 = data.substring(30, 32);
		// String zdif1 = data.substring(36, 38);
		// String zdif2 = data.substring(34, 36);
		// System.out.println(av);
		// System.out.println(Long.parseLong(mode, 16));
		// System.out.println(getDataBase(data.substring(32, 33), dif1+dif2));
		// System.out.println(getDataBase(data.substring(36, 37), zdif1+zdif2));
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }
//		try {
//			String data = "000118061400000248093e21af000107322e313033008b0d12120a28082d0b126416009afa0100f2fc4f001c00";
//			String dif1 = data.substring(26, 28);
//			String dif2 = data.substring(24, 26);
//			String dif = getDataBase(data.substring(26, 27), dif1 + dif2);
//			System.out.println("dif:" + dif);
//			String av = data.substring(28, 30);
//			Integer avalable = Integer.valueOf(av);
//			System.out.println(avalable);
//			String bat1 = data.substring(46, 48);
//			String bat2 = data.substring(44, 46);
//			String bat = getDataBase(data.substring(44, 45), bat1 + bat2);
//			System.out.println("bat:" + bat);
//			String soft = convertHexToString(data.substring(32, 44));
//			System.out.println("soft:" + soft);
//			String lt = String.valueOf(Integer.parseInt(data.substring(48, 50), 16));
//			System.out.println("lt:" + lt);
//			String ht = String.valueOf(Integer.parseInt(data.substring(50, 52), 16));
//			System.out.println("ht:" + ht);
//			String wl = String.valueOf(Integer.parseInt(data.substring(52, 54), 16));
//			System.out.println("wl:" + wl);
//			String wh = String.valueOf(Integer.parseInt(data.substring(54, 56), 16));
//			System.out.println("wh" + wh);
//			String ang = String.valueOf(Integer.parseInt(data.substring(56, 58), 16));
//			System.out.println("ang:" + ang);
//			String wt = String.valueOf(Integer.parseInt(data.substring(58, 60), 16));
//			System.out.println("wt:" + wt);
//			String rssi = String.valueOf(Integer.parseInt(data.substring(66, 68), 16));
//			System.out.println("rssi:" + rssi);
//			String sdi = String.valueOf(Integer.parseInt(data.substring(60, 62), 16));
//			System.out.println("sdi:" + sdi);
//			String sdo = String.valueOf(Integer.parseInt(data.substring(62, 64), 16));
//			System.out.println("sdo:" + sdo);
//			String fdi = String.valueOf(Integer.parseInt(data.substring(64, 66), 16));
//			System.out.println("fdi:" + fdi);
//			String pci1 = data.substring(88, 90);
//			String pci2 = data.substring(86, 88);
//			String pci = getDataBase(data.substring(88, 89), pci1 + pci2);
//			System.out.println("pci:" + pci);
//			String rsrp1 = data.substring(80, 82);
//			String rsrp2 = data.substring(78, 80);
//			String rsrp = getDataBase(data.substring(80, 81), rsrp1 + rsrp2);
//			System.out.println("rsrp:" + rsrp);
//			String snr1 = data.substring(84, 86);
//			String snr2 = data.substring(82, 84);
//			String snr = getDataBase(data.substring(84, 85), snr1 + snr2);
//			System.out.println("snr:" + snr);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println("-1--1".split("-")[1]);
		String s = "48000019180000120022656400b56201075c00c839bf1be40705080912133701000000d67501000383ea204f4c7d4817b39e12014800004e20000";
	String latBase = getHex10(s.substring(62,70));
	String lngBase = getHex10(s.substring(54,62));
	String timeStr =  getHex10(s.substring(46,54));
	String heightBase =  getHex(s.substring(70,78));
	String hmslBase =  getHex(s.substring(78,86));
	Integer lngDev =  Integer.valueOf(s.substring(86,88), 16);
	Integer latDev =  Integer.valueOf(s.substring(88,90), 16);
	Integer heightDev =  Integer.valueOf(s.substring(90,92), 16);
	Integer hmlsDev =  Integer.valueOf(s.substring(92,94), 16);
	String horAcc =  getHex(s.substring(94,102));
	String verAcc =  getHex(s.substring(102,110));
	System.out.println(horAcc);
	if(lngDev>128){
		lngDev = -256+lngDev;
	}
	if(latDev>128){
		lngDev = -256+latDev;
	}
	if(heightDev>128){
		lngDev = -256+heightDev;
	}
	if(hmlsDev>128){
		hmlsDev = -256+hmlsDev;
	}
	String lat = getDouble(latBase, latDev);
	String lng =getDouble(lngBase, lngDev);
	String height = getDouble1(heightBase, heightDev);
	String hmsl = getDouble1(hmslBase, hmlsDev);
	System.out.println(lat+"-"+lng+"-"+Double.valueOf(height)+"-"+Double.valueOf(hmsl));
	try {
		Integer aa = Integer.parseInt("E0",16);
		if(aa>128){
			aa = -256+aa;
		}
		System.out.println(aa);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	}
	
	public static  Integer get16(String index, String _d) throws Exception {
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
		
		return e;
	}
	
	public static String getDouble(String s,int a){
		Double r = Double.valueOf(a);
		Double r1 = Double.valueOf(s);
		BigDecimal f = new BigDecimal(r);
		BigDecimal f1 = new BigDecimal(r1);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()/1000000000;
		double g1 = f1.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g+g1);
		return result;
	}
	
	public static String getDouble1(String s,int a){
		Double r = Double.valueOf(a);
		Double r1 = Double.valueOf(s);
		BigDecimal f = new BigDecimal(r);
		BigDecimal f1 = new BigDecimal(r1);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()/10;
		double g1 = f1.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g+g1);
		return result;
	}
	
	public static String  getHex10(String str) {//0DE0FE43-A7C52512-F8350000
		String s1 = str.substring(6,8);
		String s2 = str.substring(4,6);
		String s3 = str.substring(2,4);
		String s4 = str.substring(0,2);
		String str2 = s1+s2+s3+s4;
		Integer i = Integer.valueOf(str2,16);
		Double r = Double.valueOf(i);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()/10000000;
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}
	
	public static String  getHex(String str) {//0DE0FE43-A7C52512
		String s1 = str.substring(6,8);
		String s2 = str.substring(4,6);
		String s3 = str.substring(2,4);
		String s4 = str.substring(0,2);
		String str2 = s1+s2+s3+s4;
		return Integer.valueOf(str2,16).toString();
	}
	
	public static String  getHex1(String str) {//0DE0FE43-A7C52512
		String s1 = str.substring(6,8);
		String s2 = str.substring(4,6);
		String s3 = str.substring(2,4);
		String s4 = str.substring(0,2);
		String str2 = s1+s2+s3+s4;
		Integer s = Integer.valueOf(str2,16)/10;
		return s.toString();
	}
	
	public static String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

}
