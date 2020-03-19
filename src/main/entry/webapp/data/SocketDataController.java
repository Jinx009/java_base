package main.entry.webapp.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import service.GnssDeviceService;
import service.GnssLogService;
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
	
	@RequestMapping(path = "/deviceData")
	@ResponseBody
	public Resp<?> deviceData() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssDeviceService.findAll());
		} catch (Exception e) {
			log.error("error:{}",e);
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
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/device")
	public String device() {
		return "/device";
	}
	
	@RequestMapping(path = "/log")
	public String log() {
		return "/log";
	}

	
	@RequestMapping(path = "/rec")
	@ResponseBody
	public Resp<?> baseDataTcpServer(String str) {
		try {
			log.warn("socket data rec:{}",str);
			String head = str.substring(0,2);
			if("48".equals(head)) {
				String mac = str.substring(4,20);
				GnssDevice gnssDevice = gnssDeviceService.findByMac(mac);
				if(gnssDevice==null) {
					gnssDevice = new GnssDevice();
					gnssDevice.setCreateTime(new Date());
					gnssDevice.setUpdateTime(new Date());
					gnssDevice.setMac(mac);
					gnssDeviceService.save(gnssDevice);
					gnssDevice = gnssDeviceService.findByMac(mac);
				}
				String cmd = str.substring(20,22);
				if("67".equals(cmd)) {//PVB
					String fixTypeStr = str.substring(78,80);
					String fixStatusStr = str.substring(80,82);
					String numStr = str.substring(84,86);
					String lngStr = str.substring(86,94);
					String latStr = str.substring(94,102);
					String height = str.substring(102,110);
					String hmsl = str.substring(110,118);
					String horAccStr = str.substring(118,126);
					String verAccStr = str.substring(126,134);
					String yearStr = str.substring(46,50);
					String monthStr = str.substring(50,52);
					String dayStr = str.substring(52,54);
					String hourStr = str.substring(54,56);
					String minStr = str.substring(56,58);
					String secStr = str.substring(58,60);
					GnssLog gnssLog = new GnssLog();
					gnssLog.setFixStatus(Integer.valueOf(Integer.valueOf(fixStatusStr,16)/64).toString());
					gnssLog.setFixType(Integer.valueOf(fixTypeStr,16));
					gnssLog.setHeight(getHex(height));
					gnssLog.setHmsl(getHex(hmsl));
					gnssLog.setDataTime(getHex4(yearStr)+"/"+Integer.valueOf(monthStr,16)+"/"+Integer.valueOf(dayStr,16)+" "+Integer.valueOf(hourStr,16)+":"+Integer.valueOf(minStr,16)+":"+Integer.valueOf(secStr,16));
					gnssLog.setHorAcc(getHex(horAccStr));
					gnssLog.setVerAcc(getHex(verAccStr));
					gnssLog.setLng(getHex10(lngStr));
					gnssLog.setLat(getHex10(latStr));
					gnssLog.setCreateTime(new Date());
					gnssLog.setMac(mac);
					if(StringUtil.isNotBlank(gnssDevice.getLat())) {
						gnssLog.setDistance(MapUtils.GetDistance(getDouble(gnssLog.getLat()), getDouble(gnssLog.getLat()), getDouble(gnssDevice.getLat()), getDouble(gnssDevice.getLat())));
					}
					gnssLog.setNum(Integer.valueOf(numStr,16));
					gnssLogService.save(gnssLog);
					gnssDevice.setFixStatus(Integer.valueOf(Integer.valueOf(fixStatusStr,16)/64).toString());
					gnssDevice.setFixType(Integer.valueOf(fixTypeStr,16));
					gnssDevice.setHeight(getHex(height));
					gnssDevice.setHmsl(getHex(hmsl));
					gnssDevice.setHorAcc(getHex(horAccStr));
					gnssDevice.setVerAcc(getHex(verAccStr));
					gnssDevice.setLng(getHex10(lngStr));
					gnssDevice.setLat(getHex10(latStr));
					gnssDevice.setDataTime(gnssLog.getDataTime());
					gnssDevice.setNum(Integer.valueOf(numStr,16));
					gnssDeviceService.updateTime(gnssDevice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String  getHex(String str) {//0DE0FE43-A7C52512
		String s1 = str.substring(6,8);
		String s2 = str.substring(4,6);
		String s3 = str.substring(2,4);
		String s4 = str.substring(0,2);
		String str2 = s1+s2+s3+s4;
		return Integer.valueOf(str2,16).toString();
	}
	
	private String  getHex4(String str) {//0DE0FE43-A7C52512
		String s1 = str.substring(2,4);
		String s2 = str.substring(0,2);
		String str2 = s1+s2;
		return Integer.valueOf(str2,16).toString();
	}
	
	
	private String  getHex10(String str) {//0DE0FE43-A7C52512-F8350000
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
	
	private Double getDouble(String s) {
		Double r = Double.valueOf(s);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
		return g;
	}
	
	public static void main(String[] args) {
//		System.out.println(getHex10("A7C52512"));
	}
	
}
