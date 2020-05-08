package main.entry.webapp.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.StringUtil;
import database.model.GnssDevice;
import database.model.GnssLog;
import database.model.SocketConnLog;
import service.GnssDeviceService;
import service.GnssLogService;
import service.SocketConnLogService;
import utils.MapUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/d")
public class SocketDataController {

	private static final Logger log = LoggerFactory.getLogger(SocketDataController.class);

	@Autowired
	private GnssDeviceService gnssDeviceService;
	@Autowired
	private GnssLogService gnssLogService;
	@Autowired
	private SocketConnLogService socketConnLogService;

	@RequestMapping(path = "/logExcelData")
	@ResponseBody
	public Resp<?> logExcelData(String startDate, String endDate, String mac, Integer fixType, Integer fixStatus,
			Integer horMin, Integer horMax, String type) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(
					gnssLogService.getByDate(startDate, endDate, mac, fixType, fixStatus, horMin, horMax, type));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/deviceData")
	@ResponseBody
	public Resp<?> deviceData() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssDeviceService.findAll());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/openDeviceData")
	@ResponseBody
	public Resp<?> openDeviceData() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssDeviceService.openDeviceData());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/openLogData")
	@ResponseBody
	public Resp<?> openLogData(String mac) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssLogService.openLogData(1, mac));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/logData")
	@ResponseBody
	public Resp<?> logData() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssLogService.find(1));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/socketData")
	@ResponseBody
	public Resp<?> socketData() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(socketConnLogService.find(1).getList());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/device")
	public String device() {
		return "/device";
	}

	@RequestMapping(path = "/height")
	public String height() {
		return "/height";
	}

	@RequestMapping(path = "/point")
	public String point() {
		return "/point";
	}

	@RequestMapping(path = "/logExcel")
	public String logExcel() {
		return "/logExcel";
	}

	@RequestMapping(path = "/logMap")
	public String logMap() {
		return "/logMap";
	}

	@RequestMapping(path = "/socket")
	public String socket() {
		return "/socket";
	}

	@RequestMapping(path = "/log")
	public String log() {
		return "/log";
	}

	@RequestMapping(path = "/enu")
	public String enu() {
		return "/enu";
	}

	@RequestMapping(path = "/socketSave")
	@ResponseBody
	public String socketSave(String status, String ip, String clientPort, String connPort) {
		SocketConnLog socketConnLog = new SocketConnLog();
		socketConnLog.setCreateTime(new Date());
		socketConnLog.setIp(ip);
		socketConnLog.setClientPort(clientPort);
		socketConnLog.setConnPort(connPort);
		socketConnLog.setStatus(status);
		socketConnLog.setMac("");
		socketConnLogService.save(socketConnLog);
		return "log";
	}

	@RequestMapping(path = "/rec")
	@ResponseBody
	public Resp<?> baseDataTcpServer(String str, String ip, String clientPort, String connPort) {
		try {
			log.warn("socket data rec:{}", str);
			String head = str.substring(0, 2);
			if ("48".equals(head)) {
				String mac = str.substring(4, 20);
				GnssDevice gnssDevice = gnssDeviceService.findByMac(mac);
				SocketConnLog socketConnLog = socketConnLogService.findBy(ip, clientPort, connPort);
				if (socketConnLog != null) {
					socketConnLog.setMac(mac);
					socketConnLogService.update(socketConnLog);
				}
				if (gnssDevice == null) {
					gnssDevice = new GnssDevice();
					gnssDevice.setCreateTime(new Date());
					gnssDevice.setUpdateTime(new Date());
					gnssDevice.setMac(mac);
					gnssDevice.setX(0.00);
					gnssDeviceService.save(gnssDevice);
					gnssDevice.setBaseLat(0.00);
					gnssDevice.setBaseLng(0.00);
					gnssDevice.setBaseHeight(0.00);
					gnssDevice.setBaseX(0.00);
					gnssDevice.setBaseY(0.00);
					gnssDevice.setBaseZ(0.00);
					gnssDevice = gnssDeviceService.findByMac(mac);
				}
				String[] arrs = str.split("4800" + mac);
				for (int i = 1; i < arrs.length; i++) {
					String s = "4800" + mac + arrs[i];
					try {
						String cmd = s.substring(20, 22);
						if ("67".equals(cmd)) {// PVB
							if (s.length() >= 220) {
								String fixTypeStr = s.substring(78, 80);
								String fixStatusStr = s.substring(80, 82);
								String numStr = s.substring(84, 86);
								String lngStr = s.substring(86, 94);
								String latStr = s.substring(94, 102);
								String height = s.substring(102, 110);
								String hmsl = s.substring(110, 118);
								String horAccStr = s.substring(118, 126);
								String verAccStr = s.substring(126, 134);
								String yearStr = s.substring(46, 50);
								String monthStr = s.substring(50, 52);
								String dayStr = s.substring(52, 54);
								String hourStr = s.substring(54, 56);
								String minStr = s.substring(56, 58);
								String secStr = s.substring(58, 60);
								GnssLog gnssLog = new GnssLog();
								gnssLog.setFixStatus(
										Integer.valueOf(Integer.valueOf(fixStatusStr, 16) / 64).toString());
								gnssLog.setFixType(Integer.valueOf(fixTypeStr, 16));
								gnssLog.setHeight(getHex(height));
								gnssLog.setHmsl(getHex(hmsl));
								String dataTime = getHex4(yearStr) + "-" + Integer.valueOf(monthStr, 16) + "-"
										+ Integer.valueOf(dayStr, 16) + " " + (Integer.valueOf(hourStr, 16) + 8) + ":"
										+ Integer.valueOf(minStr, 16) + ":" + Integer.valueOf(secStr, 16);
								gnssLog.setDataTime(dataTime);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								gnssLog.setDateTime(sdf.parse(dataTime));
								gnssLog.setHorAcc(getHex(horAccStr));
								gnssLog.setVerAcc(getHex(verAccStr));
								gnssLog.setLng(getHex10(lngStr));
								gnssLog.setLat(getHex10(latStr));
								gnssLog.setCreateTime(new Date());
								gnssLog.setMac(mac);
								gnssLog.setX(0.00);
								gnssLog.setY(0.00);
								gnssLog.setZ(0.00);
								gnssLog.setEnuX(0.00);
								gnssLog.setEnuY(0.00);
								gnssLog.setEnuZ(0.00);
								gnssLog.setXDev(0.00);
								gnssLog.setYDev(0.00);
								gnssLog.setZDev(0.00);
								gnssLog.setDataType(0);
								gnssLog.setType("1");
								if (StringUtil.isNotBlank(gnssDevice.getLat())) {
									double[] d = MapUtils.WGS84toECEF(getDoubleValue(gnssLog.getLat()),
											getDoubleValue(gnssLog.getLng()), getDoubleValueMm(gnssLog.getHeight()));
									gnssLog.setX(d[0] * 1000);
									gnssLog.setY(d[1] * 1000);
									gnssLog.setZ(d[2] * 1000);
									gnssLog.setDistance(MapUtils.GetDistance(getDouble(gnssLog.getLat()),
											getDouble(gnssLog.getLat()), getDouble(gnssDevice.getLat()),
											getDouble(gnssDevice.getLat())));
									gnssLog.setXDev(gnssLog.getX() - gnssDevice.getBaseX());
									gnssLog.setYDev(gnssLog.getY() - gnssDevice.getBaseY());
									gnssLog.setZDev(gnssLog.getZ() - gnssDevice.getBaseZ());
									gnssLog.setDataType(1);
									if ((gnssLog.getXDev() > 500 || gnssLog.getXDev() < -500)
											&& Integer.valueOf(gnssLog.getHorAcc()) < 15 && gnssLog.getFixType() == 3
											&& "2".equals(gnssLog.getFixStatus())) {
										gnssDevice.setBaseHeight(Double.valueOf(gnssLog.getHeight()) / 1000);
										gnssDevice.setBaseLat(Double.valueOf(gnssLog.getLat()));
										gnssDevice.setBaseLng(Double.valueOf(gnssLog.getLng()));
										gnssDevice.setBaseX(gnssLog.getX());
										gnssDevice.setBaseY(gnssLog.getY());
										gnssDevice.setBaseZ(gnssLog.getZ());
									}
									if (gnssDevice.getBaseLat() != 0.00) {
										double[] arr = MapUtils.wgs84ToEcef(getDoubleValue(gnssLog.getLat()),
												getDoubleValue(gnssLog.getLng()),
												getDoubleValueMm(gnssLog.getHeight()));
										double[] arr1 = MapUtils.ecefToEnu(arr[0], arr[1], arr[2],
												gnssDevice.getBaseLat(), gnssDevice.getBaseLng(),
												gnssDevice.getBaseHeight());
										gnssLog.setEnuX(new BigDecimal(arr1[0]).setScale(10, BigDecimal.ROUND_HALF_UP)
												.doubleValue());
										gnssLog.setEnuY(new BigDecimal(arr1[1]).setScale(10, BigDecimal.ROUND_HALF_UP)
												.doubleValue());
										gnssLog.setEnuZ(new BigDecimal(arr1[2]).setScale(10, BigDecimal.ROUND_HALF_UP)
												.doubleValue());
									}
								}
								gnssLog.setNum(Integer.valueOf(numStr, 16));
								gnssLogService.save(gnssLog);
								gnssDevice.setFixStatus(
										Integer.valueOf(Integer.valueOf(fixStatusStr, 16) / 64).toString());
								gnssDevice.setFixType(Integer.valueOf(fixTypeStr, 16));
								gnssDevice.setHeight(getHex(height));
								gnssDevice.setHmsl(getHex(hmsl));
								gnssDevice.setHorAcc(getHex(horAccStr));
								gnssDevice.setVerAcc(getHex(verAccStr));
								gnssDevice.setLng(getHex10(lngStr));
								gnssDevice.setLat(getHex10(latStr));
								gnssDevice.setDataTime(gnssLog.getDataTime());
								gnssDevice.setNum(Integer.valueOf(numStr, 16));
								gnssDevice.setX(gnssLog.getX());
								gnssDevice.setY(gnssLog.getY());
								gnssDevice.setZ(gnssLog.getZ());
								gnssDeviceService.updateTime(gnssDevice);
							}
						} else if ("65".equals(cmd)) { // 高精度
							if (s.length() >= 110) {
								String lngBase = getHex10(s.substring(54, 62));
								String latBase = getHex10(s.substring(62, 70));
								String heightBase = getHex(s.substring(70, 78));
								String hmslBase = getHex(s.substring(78, 86));
								String lngDev = s.substring(86, 88);
								String latDev = s.substring(88, 90);
								String heightDev = s.substring(90, 92);
								String hmlsDev = s.substring(92, 94);
								Double hor = Double.valueOf(getHex(s.substring(94, 102)));
								Double ver = Double.valueOf(getHex(s.substring(102, 110)));
								String horAcc = String.valueOf(hor / 10);
								String verAcc = String.valueOf(ver / 10);
								String lat = getDouble(latBase, latDev);
								String lng = getDouble(lngBase, lngDev);
								String height = getDouble1(heightBase, heightDev);
								String hmsl = getDouble1(hmslBase, hmlsDev);
								GnssLog gnssLog = new GnssLog();
								gnssLog.setFixStatus("-1");
								gnssLog.setFixType(-1);
								gnssLog.setHeight(height);
								gnssLog.setHmsl(hmsl);
								gnssLog.setDataTime("");
								gnssLog.setDateTime(new Date());
								gnssLog.setHorAcc(horAcc);
								gnssLog.setVerAcc(verAcc);
								gnssLog.setLng(lng);
								gnssLog.setLat(lat);
								gnssLog.setCreateTime(new Date());
								gnssLog.setMac(mac);
								gnssLog.setX(0.00);
								gnssLog.setY(0.00);
								gnssLog.setZ(0.00);
								gnssLog.setEnuX(0.00);
								gnssLog.setEnuY(0.00);
								gnssLog.setEnuZ(0.00);
								gnssLog.setXDev(0.00);
								gnssLog.setYDev(0.00);
								gnssLog.setZDev(0.00);
								gnssLog.setDataType(0);
								gnssLog.setType("2");
								if (StringUtil.isNotBlank(gnssDevice.getLat())) {
									double[] d = MapUtils.WGS84toECEF(getDoubleValue(gnssLog.getLat()),
											getDoubleValue(gnssLog.getLng()), getDoubleValueMm(gnssLog.getHeight()));
									gnssLog.setX(d[0] * 1000);
									gnssLog.setY(d[1] * 1000);
									gnssLog.setZ(d[2] * 1000);
									gnssLog.setDistance(MapUtils.GetDistance(getDouble(gnssLog.getLat()),
											getDouble(gnssLog.getLat()), getDouble(gnssDevice.getLat()),
											getDouble(gnssDevice.getLat())));
									gnssLog.setXDev(gnssLog.getX() - gnssDevice.getBaseX());
									gnssLog.setYDev(gnssLog.getY() - gnssDevice.getBaseY());
									gnssLog.setZDev(gnssLog.getZ() - gnssDevice.getBaseZ());
									gnssLog.setDataType(1);
									if ((gnssLog.getXDev() > 500 || gnssLog.getXDev() < -500)
											&& Integer.valueOf(gnssLog.getHorAcc()) < 15 && gnssLog.getFixType() == 3
											&& "2".equals(gnssLog.getFixStatus())) {
										gnssDevice.setBaseHeight(Double.valueOf(gnssLog.getHeight()) / 1000);
										gnssDevice.setBaseLat(Double.valueOf(gnssLog.getLat()));
										gnssDevice.setBaseLng(Double.valueOf(gnssLog.getLng()));
										gnssDevice.setBaseX(gnssLog.getX());
										gnssDevice.setBaseY(gnssLog.getY());
										gnssDevice.setBaseZ(gnssLog.getZ());
									}
									if (gnssDevice.getBaseLat() != 0.00) {
										double[] arr = MapUtils.wgs84ToEcef(getDoubleValue(gnssLog.getLat()),
												getDoubleValue(gnssLog.getLng()),
												getDoubleValueMm(gnssLog.getHeight()));
										double[] arr1 = MapUtils.ecefToEnu(arr[0], arr[1], arr[2],
												gnssDevice.getBaseLat(), gnssDevice.getBaseLng(),
												gnssDevice.getBaseHeight());
										gnssLog.setEnuX(new BigDecimal(arr1[0]).setScale(10, BigDecimal.ROUND_HALF_UP)
												.doubleValue());
										gnssLog.setEnuY(new BigDecimal(arr1[1]).setScale(10, BigDecimal.ROUND_HALF_UP)
												.doubleValue());
										gnssLog.setEnuZ(new BigDecimal(arr1[2]).setScale(10, BigDecimal.ROUND_HALF_UP)
												.doubleValue());
									}
								}
								gnssLog.setNum(0);
								gnssLogService.save(gnssLog);
								gnssDevice.setFixStatus("-1");
								gnssDevice.setFixType(0);
								gnssDevice.setHeight(gnssLog.getHeight());
								gnssDevice.setHmsl(gnssLog.getHmsl());
								gnssDevice.setHorAcc(horAcc);
								gnssDevice.setVerAcc(verAcc);
								gnssDevice.setLng(lng);
								gnssDevice.setLat(lat);
								gnssDevice.setDataTime(gnssLog.getDataTime());
								gnssDevice.setNum(0);
								gnssDevice.setX(gnssLog.getX());
								gnssDevice.setY(gnssLog.getY());
								gnssDevice.setZ(gnssLog.getZ());
								gnssDeviceService.updateTime(gnssDevice);
							}
						}
					} catch (Exception e) {
						log.error("解析错误:{}", e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getDouble(String s, String a) {
		Integer aa = Integer.parseInt(a, 16);
		if (aa > 128) {
			aa = -256 + aa;
		}
		Double r = Double.valueOf(aa);
		Double r1 = Double.valueOf(s);
		BigDecimal f = new BigDecimal(r);
		BigDecimal f1 = new BigDecimal(r1);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue() / 1000000000;
		double g1 = f1.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g + g1);
		return result;
	}

	private String getDouble1(String s, String a) {
		Integer aa = Integer.parseInt(a, 16);
		if (aa > 128) {
			aa = -256 + aa;
		}
		Double r = Double.valueOf(aa);
		Double r1 = Double.valueOf(s);
		BigDecimal f = new BigDecimal(r);
		BigDecimal f1 = new BigDecimal(r1);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue() / 10;
		double g1 = f1.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g + g1);
		return result;
	}

	private String getHex(String str) {// 0DE0FE43-A7C52512
		String s1 = str.substring(6, 8);
		String s2 = str.substring(4, 6);
		String s3 = str.substring(2, 4);
		String s4 = str.substring(0, 2);
		String str2 = s1 + s2 + s3 + s4;
		return Integer.valueOf(str2, 16).toString();
	}

	private String getHex4(String str) {// 0DE0FE43-A7C52512
		String s1 = str.substring(2, 4);
		String s2 = str.substring(0, 2);
		String str2 = s1 + s2;
		return Integer.valueOf(str2, 16).toString();
	}

	private double getDoubleValue(String s) {
		// BigDecimal f = new BigDecimal(Double.valueOf(s));
		// return f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()/1000;
		return Double.valueOf(s);
	}

	private double getDoubleValueMm(String s) {
		BigDecimal f = new BigDecimal(Double.valueOf(s));
		return f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue() / 1000;
	}

	// private double getDoubleDev(double x1,double x2) {
	// BigDecimal f = new BigDecimal(x1-x2);
	// return f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
	// }

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

	private Double getDouble(String s) {
		Double r = Double.valueOf(s);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		return g;
	}

	public static void main(String[] args) {

	}

}
