package main.entry.webapp.data.qj;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.qj.QjDevice;
import database.models.qj.QjDeviceLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.qj.QjDeviceLogService;
import service.basicFunctions.qj.QjDeviceService;
import utils.BaseConstant;
import utils.Resp;
import utils.msg.AlimsgUtils;

@Controller
@RequestMapping(value = "/home/cloud/qj")
public class QingjiaoDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(QingjiaoDataController.class);

	@Autowired
	private QjDeviceService qjDeviceService;
	@Autowired
	private QjDeviceLogService qjDeviceLogService;

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(qjDeviceService.findList());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/logs")
	@ResponseBody
	public Resp<?> logs(String mac, String date) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(qjDeviceLogService.nearList(mac, date));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/datas")
	@ResponseBody
	public Resp<?> datas(String mac, String date,String tem) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(qjDeviceLogService.nearList(mac, date,tem));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(String sn, String mobilePhone, Integer businessType, Integer noticeType, Integer doneType,
			String longitude, String latitude, String address) {
		Resp<?> resp = new Resp<>(false);
		try {
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice != null) {
				if (StringUtil.isNotBlank(mobilePhone)) {
					qjDevice.setMobilePhone(mobilePhone);
				}
				if (businessType != null) {
					qjDevice.setBusinessType(businessType);
				}
				if (noticeType != null) {
					qjDevice.setNoticeType(noticeType);
				}
				if (doneType != null) {
					qjDevice.setDoneType(doneType);
				}
				if (StringUtil.isNotBlank(latitude)) {
					qjDevice.setLatitude(latitude);
				}
				if (StringUtil.isNotBlank(longitude)) {
					qjDevice.setLongitude(longitude);
				}
				if (StringUtil.isNotBlank(address)) {
					qjDevice.setAddress(address);
				}
				qjDeviceService.update(qjDevice);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	/**
	 * 直川倾角
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping(path = "/zhanway/push/zcqj")
	@ResponseBody
	public Resp<?> zcqj(String data) {
		Resp<?> resp = new Resp<>(true);
		try {
			log.warn("data:---zc--{}", data);
			String cmd = data.substring(5, 6);
			String sn1 = data.substring(20, 22);
			String sn2 = data.substring(18, 20);
			String sn3 = data.substring(16, 18);
			String sn4 = data.substring(14, 16);
			long sn = Long.parseLong(sn1 + sn2 + sn3 + sn4, 16);
			if (cmd.equals("7")) {
				cmd = "心跳";
			} else if (cmd.equals("8")) {
				cmd = "报警";
			}
			String x1 = data.substring(38, 40);
			String x2 = data.substring(36, 38);
			String x3 = data.substring(34, 36);
			String x4 = data.substring(32, 34);
			String y1 = data.substring(50, 52);
			String y2 = data.substring(48, 50);
			String y3 = data.substring(46, 48);
			String y4 = data.substring(44, 46);
			String x = hexToFloat(x1 + x2 + x3 + x4);
			String y = hexToFloat(y1 + y2 + y3 + y4);
			String w1 = data.substring(58, 60);
	        String w2 = data.substring(56, 58);
	        Double w = Double.valueOf(Long.parseLong(w1 + w2, 16)) / 100;
	        String b1 = data.substring(66, 68);
	        String b2 = data.substring(64, 66);
	        Double bat = Double.valueOf(Long.parseLong(b1 + b2, 16)) / 100;
			QjDeviceLog log = new QjDeviceLog();
			log.setBaseAcceX("");
			log.setBaseAcceY("");
			log.setBaseAcceZ("");
			log.setBaseX(x);
			log.setBaseY(y);
			log.setBaseZ("");
			log.setTem(String.valueOf(w));
			log.setCreateTime(new Date());
			log.setHardV("");
			log.setSoftV("");
			log.setSnValue(String.valueOf(sn));
			log.setType(cmd);
			log.setPci("");
			log.setRsrp("");
			log.setRssi("");
			log.setSnr("");
			log.setVoltage(String.valueOf(bat));
			log.setType(cmd);
			qjDeviceLogService.save(log);
		} catch (Exception e) {
			log.error("error:{}", e);
		}

		return resp;
	}

	/**
	 * 潮州带温度
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping(path = "/zhanway/push/1_0")
	@ResponseBody
	public Resp<?> pushZhanwayV1_0(String data) {
		try {
			String sn = data.substring(0, 16);
			String type = data.substring(16, 18);
			if (type.equals("68")) {
				type = "报警";
			} else {
				type = "心跳";
			}
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice == null) {
				qjDevice = new QjDevice();
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setBaseAcceX(getDataBase(data.substring(18, 19), data.substring(18, 22)) + "/10000");
				qjDevice.setAcceXType(Integer.valueOf(data.substring(22, 24)));
				qjDevice.setBaseAcceY(getDataBase(data.substring(24, 25), data.substring(24, 28)) + "/10000");
				qjDevice.setAcceYType(Integer.valueOf(data.substring(28, 30)));
				qjDevice.setBaseAcceZ(getDataBase(data.substring(30, 31), data.substring(30, 34)) + "/10000");
				qjDevice.setAcceZType(Integer.valueOf(data.substring(34, 36)));
				qjDevice.setBaseX(getData100(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setXValue(getData100(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setBaseY(getData100(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setYValue(getData100(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setXType(Integer.valueOf(data.substring(40, 42)));
				qjDevice.setYType(Integer.valueOf(data.substring(46, 48)));
				qjDevice.setVoltage(getData(data.substring(48, 49), data.substring(48, 52)));
				qjDevice.setDoneType(1);
				qjDevice.setNoticeType(0);
				qjDevice.setBusinessType(1);
				qjDeviceService.save(qjDevice);

			} else {
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setBaseAcceX(getDataBase(data.substring(18, 19), data.substring(18, 22)) + "/10000");
				qjDevice.setAcceXType(Integer.valueOf(data.substring(22, 24)));
				qjDevice.setBaseAcceY(getDataBase(data.substring(24, 25), data.substring(24, 28)) + "/10000");
				qjDevice.setAcceYType(Integer.valueOf(data.substring(28, 30)));
				qjDevice.setBaseAcceZ(getDataBase(data.substring(30, 31), data.substring(30, 34)) + "/10000");
				qjDevice.setAcceZType(Integer.valueOf(data.substring(34, 36)));
				qjDevice.setXValue(
						String.valueOf(Double.valueOf(getData100(data.substring(36, 37), data.substring(36, 40)))
								- Double.valueOf(qjDevice.getBaseX())));
				qjDevice.setBaseX(getData100(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setYValue(
						String.valueOf(Double.valueOf(getData100(data.substring(42, 43), data.substring(42, 46)))
								- Double.valueOf(qjDevice.getBaseY())));
				qjDevice.setBaseY(getData100(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setXType(Integer.valueOf(data.substring(40, 42)));
				qjDevice.setYType(Integer.valueOf(data.substring(46, 48)));
				qjDevice.setVoltage(getData(data.substring(48, 49), data.substring(48, 52)));
				qjDevice.setDoneType(0);
				qjDeviceService.update(qjDevice);
				QjDeviceLog qjDeviceLog = new QjDeviceLog();
				qjDeviceLog.setBaseX(qjDevice.getBaseX());
				qjDeviceLog.setBaseY(qjDevice.getBaseY());
				qjDeviceLog.setSnValue(qjDevice.getSnValue());
				qjDeviceLog.setType(qjDevice.getType());
				qjDeviceLog.setVoltage(qjDevice.getVoltage());
				qjDeviceLog.setXType(qjDevice.getXType());
				qjDeviceLog.setYType(qjDevice.getYType());
				qjDeviceLog.setAcceXType(qjDevice.getAcceXType());
				qjDeviceLog.setAcceYType(qjDevice.getAcceYType());
				qjDeviceLog.setAcceZType(qjDevice.getAcceZType());
				qjDeviceLog.setBaseAcceX(qjDevice.getBaseAcceX());
				qjDeviceLog.setBaseAcceY(qjDevice.getBaseAcceY());
				qjDeviceLog.setBaseAcceZ(qjDevice.getBaseAcceZ());
				if (data.length() > 52) {
					if ("心跳".equals(qjDevice.getType())) {
						qjDeviceLog.setTem(getData100(data.substring(52, 53), data.substring(52, 56)));
					} else {
						qjDeviceLog.setTem("");
					}
					qjDeviceLog.setRssi(data.substring(56, 58));
					qjDeviceLog.setPci(String
							.valueOf(Double.valueOf(getData(data.substring(58, 59), data.substring(58, 62))) * 1000));
					qjDeviceLog.setRsrp(String
							.valueOf(Double.valueOf(getData(data.substring(62, 63), data.substring(62, 66))) * 1000));
					qjDeviceLog.setSnr(String
							.valueOf(Double.valueOf(getData(data.substring(66, 67), data.substring(66, 70))) * 1000));
				}

				QjDeviceLog qjDeviceLog2 = qjDeviceLogService.getNearBySn(sn);
				if ("报警".equals(qjDevice.getType())) {
					if ((qjDeviceLog2 != null && !qjDeviceLog2.getBaseX().equals(qjDeviceLog.getBaseX())
							&& !qjDeviceLog2.getBaseY().equals(qjDeviceLog.getBaseY())) || qjDeviceLog2 == null) {
						if (qjDevice.getMobilePhone() != null && !"".equals(qjDevice.getMobilePhone())
								&& qjDevice.getNoticeType() != null && qjDevice.getNoticeType() == 1) {
							if (qjDevice.getBusinessType() == 0) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							}
						}
					}
				}
				qjDeviceLogService.save(qjDeviceLog);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return new Resp<>(true);
	}

	/**
	 * 浮点数按IEEE754标准转16进制字符串
	 * 
	 * @param f
	 * @return
	 */
	public String FloatToHexString(float f) {
		int i = Float.floatToIntBits(f);
		String str = Integer.toHexString(i).toUpperCase();
		return str;
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

	public static void main(String[] args) throws Exception {
		System.out.println(new QingjiaoDataController().getData100("0", "0C6F"));
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public String convertStringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}

	public String convertHexToString(String hex) {
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

	/**
	 * 第三版数据上报格式20190703
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping(path = "/zhanway/push/3_0")
	@ResponseBody
	public Resp<?> pushZhanwayV3_0(String data) {
		try {
			String sn = data.substring(0, 16);
			if(sn.equals("0009190906000062")){
				sn = "0009190906000063";
			}
			String cmd = data.substring(20, 22);
			String flag = data.substring(24, 26);
			if (cmd.equals("68")) {
				cmd = "心跳_" + flag;
				String acc_x = hexToFloat(data.substring(26, 34));
				String acc_y = hexToFloat(data.substring(34, 42));
				String acc_z = hexToFloat(data.substring(42, 50));
				String x = getData100(data.substring(50, 51), data.substring(50, 54));
				String y = getData100(data.substring(54, 55), data.substring(54, 58));
				String z = getData100(data.substring(58, 59), data.substring(58, 62));
				String bat = getData(data.substring(62, 63), data.substring(62, 66));
				String tem = String.valueOf(Integer.parseInt(data.substring(66, 68), 16));
				String rssi = String.valueOf(Integer.parseInt(data.substring(68, 70), 16));
				String rsrp = String
						.valueOf(Double.valueOf(getDataBase(data.substring(70, 71), data.substring(70, 74))));
				String snr = String
						.valueOf(Double.valueOf(getDataBase(data.substring(74, 75), data.substring(74, 78))));
				String pci = String
						.valueOf(Double.valueOf(getDataBase(data.substring(78, 79), data.substring(78, 82))));
				String hard = convertHexToString(data.substring(82, 94));
				String soft = convertHexToString(data.substring(94, 106));
				QjDeviceLog log = new QjDeviceLog();
				log.setBaseAcceX(acc_x);
				log.setBaseAcceY(acc_y);
				log.setBaseAcceZ(acc_z);
				log.setBaseX(x);
				log.setBaseY(y);
				log.setBaseZ(z);
				log.setCreateTime(new Date());
				log.setHardV(hard);
				log.setSoftV(soft);
				log.setSnValue(sn);
				log.setType(cmd);
				log.setPci(pci);
				log.setRsrp(rsrp);
				log.setRssi(rssi);
				log.setSnr(snr);
				log.setTem(tem);
				log.setVoltage(bat);
				log.setType(cmd);
				qjDeviceLogService.save(log);
			} else if (cmd.equals("69")) {
				cmd = "报警_" + flag;
				QjDeviceLog log = new QjDeviceLog();
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
				log.setSnValue(sn);
				log.setType(cmd);
				log.setTem(tem);
				log.setAccxMax(acc_x_max);
				log.setAccyMax(acc_y_max);
				log.setAcczMax(acc_z_max);
				log.setAccxMin(acc_x_min);
				log.setAccyMin(acc_y_min);
				log.setAcczMin(acc_z_min);
				log.setVoltage(bat);
				log.setCreateTime(new Date());
				qjDeviceLogService.save(log);
				if(sn.equals("0009190906000063")||sn.equals("0009190906000064")||sn.equals("0009190906000060")){
					DecimalFormat    df   = new DecimalFormat("######0.00");   
					Double x_d = Double.valueOf(x);
					Double y_d = Double.valueOf(y);
					Double z_d = Double.valueOf(z);
					Double angle_max = x_d;
					if(y_d>angle_max){
						angle_max = y_d;
					}
					if(z_d>angle_max){
						angle_max = z_d;
					}
					Double acc_x_d = Double.valueOf(acc_x_max);
					Double acc_y_d = Double.valueOf(acc_y_max);
					Double acc_z_d = Double.valueOf(acc_z_max);
					Double acc_max = acc_x_d;
					if(acc_y_d>angle_max){
						acc_max = acc_y_d;
					}
					if(acc_z_d>angle_max){
						acc_max = acc_z_d;
					}
					Double min_x_d = Double.valueOf(acc_x_min);
					Double min_y_d = Double.valueOf(acc_y_min);
					Double min_z_d = Double.valueOf(acc_z_min);
					String zd = df.format(min_x_d+min_y_d+min_z_d);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("angle", df.format(angle_max));
					map.put("acc_max", df.format(acc_max));
					map.put("zd",zd);
					map.put("address", "攀枝花煤矿监测点"+sn.substring(5,16));
					AlimsgUtils.sendNotice("18217700275", "SMS_174806667", "展为", JSONObject.toJSONString(map));
					AlimsgUtils.sendNotice("13918073897", "SMS_174806667", "展为", JSONObject.toJSONString(map));
				}
			} else if (cmd.equals("6A")) {
				cmd = "温度_" + flag;
				int num = Integer.valueOf(data.substring(24, 26)).intValue();
				int start = 26;
				Date date = new Date();
				if (num != 0) {
					for (int i = 0; i < num; i++) {
						start += i * 26;
						QjDeviceLog log = new QjDeviceLog();
						log.setType(cmd);
						log.setSnValue(sn);
						log.setCreateTime(date);
						log.setTem(String.valueOf(Integer.parseInt(data.substring(start, start + 2), 16)));
						log.setBaseAcceX(hexToFloat(data.substring(start + 2, start + 10)));
						log.setBaseAcceY(hexToFloat(data.substring(start + 10, start + 18)));
						log.setBaseAcceZ(hexToFloat(data.substring(start + 18, start + 26)));
						qjDeviceLogService.save(log);
					}
				}
			}

		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return new Resp<>(true);
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

	/**
	 * 普质 带z轴
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping(path = "/zhanway/push/2_0")
	@ResponseBody
	public Resp<?> pushZhanwayV2_0(String data) {
		try {
			String sn = data.substring(0, 16);
			String type = data.substring(16, 18);
			if (type.equals("68")) {
				type = "报警";
			} else {
				type = "心跳";
			}
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice == null) {
				qjDevice = new QjDevice();
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setBaseAcceX(getDataBase(data.substring(18, 19), data.substring(18, 22)) + "/10000");
				qjDevice.setAcceXType(Integer.valueOf(data.substring(22, 24)));
				qjDevice.setBaseAcceY(getDataBase(data.substring(24, 25), data.substring(24, 28)) + "/10000");
				qjDevice.setAcceYType(Integer.valueOf(data.substring(28, 30)));
				qjDevice.setBaseAcceZ(getDataBase(data.substring(30, 31), data.substring(30, 34)) + "/10000");
				qjDevice.setAcceZType(Integer.valueOf(data.substring(34, 36)));
				qjDevice.setBaseX(getData100(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setXValue(getData100(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setBaseY(getData100(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setYValue(getData100(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setXType(Integer.valueOf(data.substring(40, 42)));
				qjDevice.setYType(Integer.valueOf(data.substring(46, 48)));
				qjDevice.setVoltage(getData(data.substring(48, 49), data.substring(48, 52)));
				qjDevice.setDoneType(1);
				qjDevice.setNoticeType(0);
				qjDevice.setBusinessType(1);
				qjDeviceService.save(qjDevice);

			} else {
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setBaseAcceX(getDataBase(data.substring(18, 19), data.substring(18, 22)) + "/10000");
				qjDevice.setAcceXType(Integer.valueOf(data.substring(22, 24)));
				qjDevice.setBaseAcceY(getDataBase(data.substring(24, 25), data.substring(24, 28)) + "/10000");
				qjDevice.setAcceYType(Integer.valueOf(data.substring(28, 30)));
				qjDevice.setBaseAcceZ(getDataBase(data.substring(30, 31), data.substring(30, 34)) + "/10000");
				qjDevice.setAcceZType(Integer.valueOf(data.substring(34, 36)));
				qjDevice.setXValue(
						String.valueOf(Double.valueOf(getData100(data.substring(36, 37), data.substring(36, 40)))
								- Double.valueOf(qjDevice.getBaseX())));
				qjDevice.setBaseX(getData100(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setYValue(
						String.valueOf(Double.valueOf(getData100(data.substring(42, 43), data.substring(42, 46)))
								- Double.valueOf(qjDevice.getBaseY())));
				qjDevice.setBaseY(getData100(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setXType(Integer.valueOf(data.substring(40, 42)));
				qjDevice.setYType(Integer.valueOf(data.substring(46, 48)));
				qjDevice.setVoltage(getData(data.substring(48, 49), data.substring(48, 52)));
				qjDevice.setDoneType(0);
				qjDevice.setZValue(getData100(data.substring(70, 71), data.substring(70, 74)));
				qjDevice.setZType(Integer.valueOf(data.substring(74, 76)));
				qjDeviceService.update(qjDevice);
				QjDeviceLog qjDeviceLog = new QjDeviceLog();
				qjDeviceLog.setBaseX(qjDevice.getBaseX());
				qjDeviceLog.setBaseY(qjDevice.getBaseY());
				qjDeviceLog.setSnValue(qjDevice.getSnValue());
				qjDeviceLog.setType(qjDevice.getType());
				qjDeviceLog.setVoltage(qjDevice.getVoltage());
				qjDeviceLog.setXType(qjDevice.getXType());
				qjDeviceLog.setYType(qjDevice.getYType());
				qjDeviceLog.setAcceXType(qjDevice.getAcceXType());
				qjDeviceLog.setAcceYType(qjDevice.getAcceYType());
				qjDeviceLog.setAcceZType(qjDevice.getAcceZType());
				qjDeviceLog.setBaseAcceX(qjDevice.getBaseAcceX());
				qjDeviceLog.setBaseAcceY(qjDevice.getBaseAcceY());
				qjDeviceLog.setBaseAcceZ(qjDevice.getBaseAcceZ());
				qjDeviceLog.setZType(qjDevice.getZType());
				qjDeviceLog.setBaseZ(qjDevice.getZValue());
				if (data.length() > 52) {
					if ("心跳".equals(qjDevice.getType())) {
						qjDeviceLog.setTem(getData100(data.substring(52, 53), data.substring(52, 56)));
					} else {
						qjDeviceLog.setTem("");
					}
					qjDeviceLog.setRssi(data.substring(56, 58));
					qjDeviceLog.setPci(String
							.valueOf(Double.valueOf(getData(data.substring(58, 59), data.substring(58, 62))) * 1000));
					qjDeviceLog.setRsrp(String
							.valueOf(Double.valueOf(getData(data.substring(62, 63), data.substring(62, 66))) * 1000));
					qjDeviceLog.setSnr(String
							.valueOf(Double.valueOf(getData(data.substring(66, 67), data.substring(66, 70))) * 1000));
				}

				QjDeviceLog qjDeviceLog2 = qjDeviceLogService.getNearBySn(sn);
				if ("报警".equals(qjDevice.getType())) {
					if ((qjDeviceLog2 != null && !qjDeviceLog2.getBaseX().equals(qjDeviceLog.getBaseX())
							&& !qjDeviceLog2.getBaseY().equals(qjDeviceLog.getBaseY())) || qjDeviceLog2 == null) {
						if (qjDevice.getMobilePhone() != null && !"".equals(qjDevice.getMobilePhone())
								&& qjDevice.getNoticeType() != null && qjDevice.getNoticeType() == 1) {
							if (qjDevice.getBusinessType() == 0) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							}
						}
					}
				}
				qjDeviceLogService.save(qjDeviceLog);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return new Resp<>(true);
	}

	@RequestMapping(path = "/zhanway/push")
	@ResponseBody
	public Resp<?> pushZhanway(String data) {
		try {
			String sn = data.substring(0, 16);
			String type = data.substring(16, 18);
			if (type.equals("68")) {
				type = "报警";
			} else {
				type = "心跳";
			}
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice == null) {
				qjDevice = new QjDevice();
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setBaseAcceX(getData(data.substring(18, 19), data.substring(18, 22)));
				qjDevice.setAcceXType(Integer.valueOf(data.substring(22, 24)));
				qjDevice.setBaseAcceY(getData(data.substring(24, 25), data.substring(24, 28)));
				qjDevice.setAcceYType(Integer.valueOf(data.substring(28, 30)));
				qjDevice.setBaseAcceZ(getData(data.substring(30, 31), data.substring(30, 34)));
				qjDevice.setAcceZType(Integer.valueOf(data.substring(34, 36)));
				qjDevice.setBaseX(getData(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setXValue(getData(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setBaseY(getData(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setYValue(getData(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setXType(Integer.valueOf(data.substring(40, 42)));
				qjDevice.setYType(Integer.valueOf(data.substring(46, 48)));
				qjDevice.setVoltage(getData(data.substring(48, 49), data.substring(48, 52)));
				qjDevice.setDoneType(1);
				qjDevice.setNoticeType(0);
				qjDevice.setBusinessType(1);
				qjDeviceService.save(qjDevice);

			} else {
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setBaseAcceX(getData(data.substring(18, 19), data.substring(18, 22)));
				qjDevice.setAcceXType(Integer.valueOf(data.substring(22, 24)));
				qjDevice.setBaseAcceY(getData(data.substring(24, 25), data.substring(24, 28)));
				qjDevice.setAcceYType(Integer.valueOf(data.substring(28, 30)));
				qjDevice.setBaseAcceZ(getData(data.substring(30, 31), data.substring(30, 34)));
				qjDevice.setAcceZType(Integer.valueOf(data.substring(34, 36)));
				qjDevice.setXValue(
						String.valueOf(Double.valueOf(getData(data.substring(36, 37), data.substring(36, 40)))
								- Double.valueOf(qjDevice.getBaseX())));
				qjDevice.setBaseX(getData(data.substring(36, 37), data.substring(36, 40)));
				qjDevice.setYValue(
						String.valueOf(Double.valueOf(getData(data.substring(42, 43), data.substring(42, 46)))
								- Double.valueOf(qjDevice.getBaseY())));
				qjDevice.setBaseY(getData(data.substring(42, 43), data.substring(42, 46)));
				qjDevice.setXType(Integer.valueOf(data.substring(40, 42)));
				qjDevice.setYType(Integer.valueOf(data.substring(46, 48)));
				qjDevice.setVoltage(getData(data.substring(48, 49), data.substring(48, 52)));
				qjDevice.setDoneType(0);
				qjDeviceService.update(qjDevice);
				QjDeviceLog qjDeviceLog = new QjDeviceLog();
				qjDeviceLog.setBaseX(qjDevice.getBaseX());
				qjDeviceLog.setBaseY(qjDevice.getBaseY());
				qjDeviceLog.setSnValue(qjDevice.getSnValue());
				qjDeviceLog.setType(qjDevice.getType());
				qjDeviceLog.setVoltage(qjDevice.getVoltage());
				qjDeviceLog.setXType(qjDevice.getXType());
				qjDeviceLog.setYType(qjDevice.getYType());
				qjDeviceLog.setAcceXType(qjDevice.getAcceXType());
				qjDeviceLog.setAcceYType(qjDevice.getAcceYType());
				qjDeviceLog.setAcceZType(qjDevice.getAcceZType());
				qjDeviceLog.setBaseAcceX(qjDevice.getBaseAcceX());
				qjDeviceLog.setBaseAcceY(qjDevice.getBaseAcceY());
				qjDeviceLog.setBaseAcceZ(qjDevice.getBaseAcceZ());
				if (data.length() > 52 && data.length() < 55) {
					qjDeviceLog.setRssi(data.substring(52, 54));
					qjDeviceLog.setPci(String
							.valueOf(Double.valueOf(getData(data.substring(62, 63), data.substring(62, 66))) * 1000));
					qjDeviceLog.setRsrp(String
							.valueOf(Double.valueOf(getData(data.substring(54, 55), data.substring(54, 58))) * 1000));
					qjDeviceLog.setSnr(String
							.valueOf(Double.valueOf(getData(data.substring(58, 59), data.substring(58, 62))) * 1000));
				}

				QjDeviceLog qjDeviceLog2 = qjDeviceLogService.getNearBySn(sn);
				if ("报警".equals(qjDevice.getType())) {
					if ((qjDeviceLog2 != null && !qjDeviceLog2.getBaseX().equals(qjDeviceLog.getBaseX())
							&& !qjDeviceLog2.getBaseY().equals(qjDeviceLog.getBaseY())) || qjDeviceLog2 == null) {
						if (qjDevice.getMobilePhone() != null && !"".equals(qjDevice.getMobilePhone())
								&& qjDevice.getNoticeType() != null && qjDevice.getNoticeType() == 1) {
							if (qjDevice.getBusinessType() == 0) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							}
						}
					}
				}
				qjDeviceLogService.save(qjDeviceLog);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return new Resp<>(true);
	}

	@RequestMapping(path = "/push")
	@ResponseBody
	public Resp<?> push(String data) {
		try {
			String[] dataArray2 = data.split("");
			String[] dataArray = new String[100];
			int i = 0;
			for (String s : dataArray2) {
				if (s != null && !"".equals(s)) {
					dataArray[i] = s;
					i++;
				}
			}
			String type = dataArray[0] + dataArray[1];
			if (type.equals("68")) {
				type = "报警";
			} else {
				type = "心跳";
			}
			String sn = String.valueOf(Integer.parseInt(dataArray[2] + dataArray[3] + dataArray[4] + dataArray[5]
					+ dataArray[6] + dataArray[7] + dataArray[8] + dataArray[9], 16));
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice == null) {
				qjDevice = new QjDevice();
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setXType(Integer.valueOf(dataArray[10] + dataArray[11]));
				qjDevice.setBaseX(
						getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				qjDevice.setXValue(
						getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				qjDevice.setYType(Integer.valueOf(dataArray[16] + dataArray[17]));
				qjDevice.setYValue(
						getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]));
				qjDevice.setBaseY(
						getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]));
				qjDevice.setVoltage(
						getData(dataArray[22], dataArray[22] + dataArray[23] + dataArray[24] + dataArray[25]));
				qjDevice.setDoneType(1);
				qjDevice.setNoticeType(0);
				qjDevice.setBusinessType(1);
				qjDeviceService.save(qjDevice);

			} else {
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setXType(Integer.valueOf(dataArray[10] + dataArray[11]));
				String xValue = getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]);
				double _x = Double.valueOf(xValue) - Double.valueOf(qjDevice.getBaseX());
				qjDevice.setBaseX(xValue);
				qjDevice.setXValue(String.valueOf(_x));
				qjDevice.setYType(Integer.valueOf(dataArray[16] + dataArray[17]));
				String yValue = getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]);
				double _y = Double.valueOf(yValue) - Double.valueOf(qjDevice.getBaseY());
				qjDevice.setBaseY(yValue);
				qjDevice.setYValue(String.valueOf(_y));
				qjDevice.setVoltage(
						getData(dataArray[22], dataArray[22] + dataArray[23] + dataArray[24] + dataArray[25]));
				qjDevice.setDoneType(0);
				qjDeviceService.update(qjDevice);
				QjDeviceLog qjDeviceLog = new QjDeviceLog();
				qjDeviceLog.setBaseX(qjDevice.getBaseX());
				qjDeviceLog.setBaseY(qjDevice.getBaseY());
				qjDeviceLog.setSnValue(qjDevice.getSnValue());
				qjDeviceLog.setType(qjDevice.getType());
				qjDeviceLog.setVoltage(qjDevice.getVoltage());
				qjDeviceLog.setXType(qjDevice.getXType());
				qjDeviceLog.setYType(qjDevice.getYType());
				qjDeviceLog.setAcceXType(0);
				qjDeviceLog.setAcceYType(0);
				qjDeviceLog.setAcceZType(0);
				qjDeviceLog.setBaseAcceX("");
				qjDeviceLog.setBaseAcceY("");
				qjDeviceLog.setBaseAcceZ("");
				QjDeviceLog qjDeviceLog2 = qjDeviceLogService.getNearBySn(sn);
				if ("报警".equals(qjDevice.getType())) {
					if ((qjDeviceLog2 != null && !qjDeviceLog2.getBaseX().equals(qjDeviceLog.getBaseX())
							&& !qjDeviceLog2.getBaseY().equals(qjDeviceLog.getBaseY())) || qjDeviceLog2 == null) {
						if (qjDevice.getMobilePhone() != null && !"".equals(qjDevice.getMobilePhone())
								&& qjDevice.getNoticeType() != null && qjDevice.getNoticeType() == 1) {
							if (qjDevice.getBusinessType() == 0) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							} else if (qjDevice.getBusinessType() == 1) {
								AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE,
										BaseConstant.MESSAGE_SIGN);
							}
						}
					}
				}
				qjDeviceLogService.save(qjDeviceLog);
			}

		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return new Resp<>(true);
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
		// log.warn("result:{}", result);
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
		// log.warn("result:{}", result);
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
		// log.warn("result:{}", result);
		return result;
	}
}