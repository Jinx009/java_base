package main.entry.webapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.device.DeviceSensor;
import database.models.device.DeviceSensorInfo;
import database.models.device.vo.DeviceSensorInfoModel;
import database.models.log.LogSensorHeart;
import main.entry.webapp.BaseController;
import service.basicFunctions.device.DeviceSensorInfoService;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.log.LogSensorLogService;
import utils.StringUtil;
import utils.model.Resp;

@Controller
@RequestMapping(value = "/common/log")
public class LogsController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(LogsController.class);

	@Autowired
	private LogSensorLogService logSensorDeviceService;
	@Autowired
	private DeviceSensorInfoService deviceSesnorInfoService;
	@Autowired
	private DeviceSensorService deviceSensorService;
	@Autowired
	private LogSensorLogService logSensorLogService;

	@RequestMapping(path = "/deviceAlive", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String deviceAlive() {
		try {
			String res = "<html><body>";
			List<String> list = logSensorDeviceService.findAlive();
			if (list != null && !list.isEmpty()) {
				res += "<h2>当日激活地磁个数：" + list.size() + " 个</h2>";
				for (String s : list) {
					res += "<p style=\"font-size:16px;\">MAC：" + s + "；时间：" + logSensorDeviceService.findDate(s)
							+ "</p>";
				}
			}
			return res + "</body></html>";
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return null;
	}

	@RequestMapping(path = "wuhan")
	@ResponseBody
	public List<DeviceSensor> devices(String mac) {
		return deviceSensorService.findByParentMac(mac);
	}

	@RequestMapping(path = "install")
	@ResponseBody
	public List<DeviceSensor> install() {
		return deviceSensorService.install();
	}

	@RequestMapping(path = "updateDesc")
	@ResponseBody
	public Resp<?> updateDesc(String mac, String baseMac, String desc, String sec, String parentMac) {
		if ("Zhanway2020".equals(sec)) {
			if (mac.equals(baseMac)) {
				DeviceSensor sensor = deviceSensorService.findByMac(mac);
				sensor.setDesc(desc);
				sensor.setParentMac(parentMac);
				deviceSensorService.update(sensor);
			} else {
				DeviceSensor sensor = deviceSensorService.findByMac(mac);
				sensor.setDesc(desc);
				sensor.setCameraName("001001");
				sensor.setAreaId(64);
				sensor.setParentMac(parentMac);
				deviceSensorService.update(sensor);
				DeviceSensor sensor2 = deviceSensorService.findByMac(baseMac);
				sensor2.setAreaId(1001);
				sensor2.setParentMac("");
				deviceSensorService.update(sensor2);
			}
		} else if ("wjb".equals(sec)) {
			DeviceSensor sensor = deviceSensorService.findByMac(mac);
			Date date = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int avalable = Math.abs(sensor.getAvailable() - 1);
			sensor.setAvailable(avalable);
			sensor.setLastSeenTime(date);
			sensor.setSensorStatus(avalable);
			sensor.setMode("");
			sensor.setHappenTime(date);
			sensor.setSensorTime(sdf1.format(date));
			sensor.setVedioStatus("");
			sensor.setCph("");
			sensor.setCpColor("");
			sensor.setCameraId("");
			sensor.setPicLink("");
			sensor.setVedioTime("");
			sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
			sensor.setBluetooth("");
			sensor.setBluetoothArray("");
			deviceSensorService.update(sensor);
			logSensorLogService.saveOperationLog(sensor);
		} else {
			return new Resp<>(false);
		}
		return new Resp<>(true);
	}

	@RequestMapping(path = "updateInstall")
	@ResponseBody
	public Resp<?> updateInstall(String mac, String parentMac, String desc) {
		DeviceSensor sensor = deviceSensorService.findByMac(mac);
		if (sensor != null) {
			sensor.setCameraName("001001");
			sensor.setAreaId(64);
			sensor.setParentMac(parentMac);
			sensor.setDesc(desc);
			deviceSensorService.update(sensor);
		}
		return new Resp<>(true);
	}

	/**
	 * 查询心跳最新时间
	 * 
	 * @return
	 */
	@RequestMapping(path = "logs")
	@ResponseBody
	public List<LogSensorHeart> logs() {
		List<LogSensorHeart> logs = new ArrayList<LogSensorHeart>();
		List<DeviceSensorInfo> list = deviceSesnorInfoService.find();
		for (DeviceSensorInfo deviceSesnorInfo : list) {
			LogSensorHeart logSensorHeart = logSensorDeviceService.findByInfoMac(deviceSesnorInfo.getMac());
			if (logSensorHeart != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logSensorHeart.setMac("mac:" + logSensorHeart.getMac());
				logSensorHeart.setBaseEnergy(deviceSesnorInfo.getParkNumber());
				logSensorHeart.setUid(deviceSesnorInfo.getAddress());
				logSensorHeart.setPanId(sdf.format(logSensorHeart.getCreateTime()));
				logs.add(logSensorHeart);
			}
		}
		return logs;
	}

	/**
	 * 查询区域间心跳个数
	 * 
	 * @param date1
	 * @param time1
	 * @param date2
	 * @param time2
	 * @return
	 */
	@RequestMapping(path = "hearts")
	@ResponseBody
	public List<DeviceSensorInfoModel> list(String date1, String time1, String date2, String time2) {
		List<DeviceSensorInfoModel> models = new ArrayList<DeviceSensorInfoModel>();
		List<DeviceSensorInfo> list = deviceSesnorInfoService.find();
		for (DeviceSensorInfo device : list) {
			DeviceSensorInfoModel model = new DeviceSensorInfoModel();
			model.setMac("mac:" + device.getMac());
			model.setAddress(device.getAddress());
			model.setParkNumber(device.getParkNumber());
			model.setNum("0");
			if (StringUtil.isNotBlank(device.getMac())) {
				List<LogSensorHeart> list2 = logSensorDeviceService.findList(device.getMac(), date1, date2, time1,
						time2);
				if (list2 != null && !list2.isEmpty()) {
					LogSensorHeart log = list2.get(0);
					model.setNum(String.valueOf(list2.size()));
					model.setDif(log.getDif());
					model.setPci(log.getPci());
					model.setRsrp(log.getRsrp());
					model.setRssi(log.getRssi());
					model.setSnr(log.getSnr());
					model.setState(log.getState());
					model.setVol(log.getVol());
				}
			}
			models.add(model);
		}
		return models;
	}

	@RequestMapping(path = "sensors")
	@ResponseBody
	public List<DeviceSensor> sensorsList(String mac, String address) {
		List<DeviceSensorInfo> list = deviceSesnorInfoService.findByMacAndAddress(mac, address);
		List<DeviceSensor> sensors = new ArrayList<DeviceSensor>();
		for (DeviceSensorInfo device : list) {
			if (StringUtil.isNotBlank(device.getMac())) {
				DeviceSensor deviceSensor = deviceSensorService.findByMac(device.getMac());
				LogSensorHeart logSensorHeart = logSensorDeviceService.findByInfoMac(device.getMac());
				deviceSensor.setBaseEnergy(device.getParkNumber());
				if (logSensorHeart != null) {
					deviceSensor.setCreateTime(logSensorHeart.getCreateTime());
					deviceSensor.setDesc(logSensorHeart.getRssi());
				}
				sensors.add(deviceSensor);
			}

		}
		return sensors;
	}

	@RequestMapping(path = "devices")
	@ResponseBody
	public List<DeviceSensor> devices(String mac, String address) {
		List<DeviceSensorInfo> list = deviceSesnorInfoService.findByMacAndAddress(mac, address);
		List<DeviceSensor> sensors = new ArrayList<DeviceSensor>();
		for (DeviceSensorInfo device : list) {
			if (StringUtil.isNotBlank(device.getMac())) {
				DeviceSensor deviceSensor = deviceSensorService.findByMac(device.getMac());
				deviceSensor.setBaseEnergy(device.getParkNumber());
				if (deviceSensor != null) {
					deviceSensor.setCreateTime(deviceSensor.getLastSeenTime());
//					deviceSensor.setDesc(logSensorHeart.getRssi());
				}
				sensors.add(deviceSensor);
			}

		}
		return sensors;
	}

}