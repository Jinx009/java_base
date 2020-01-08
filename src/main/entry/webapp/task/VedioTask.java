package main.entry.webapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.device.DeviceSensor;
import database.models.log.LogSensorStatus;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.log.LogSensorLogService;
import utils.HttpUtil;
import utils.StringUtil;
import utils.baoxin.SendUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Lazy(value = false)
public class VedioTask {

	private static final Logger log = LoggerFactory.getLogger(VedioTask.class);
	private static final String VEDIO_URL = "http://58.246.184.99:8089/d/saveVedio";
	private static final String VEDIO_URL_IMG = "http://58.246.184.99:801/";

	@Autowired
	private DeviceSensorService deviceSensorService;
	@Autowired
	private LogSensorLogService logSensorLogService;

	/**
	 * 静安宝信推送心跳
	 */
//	@Scheduled(cron = "0 0 16 * * ?") // 每天中午16点推送一次
	public void sendJinganDeviceHeart() {
		try {
			log.warn("jingan device[start]");
			List<DeviceSensor> list = deviceSensorService.getSensorsByArea(1);
			SendUtils.sendDeviceHeart(list);
		} catch (Exception e) {
			log.error("jingan device[error:{}]", e);
		}
	}

	@Scheduled(fixedRate = 90 * 1000) // 每1.5分钟S执行一次
	public void sendNormal() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
			String[] macs = new String[] {
					// "0001180614000062",
					"0001180614000120", "0001180614000052", "0001180614000233",
					// "0001180614000011",
					// "00011806140000A0",
					// "00011806140000A6",
					// "000118061400007A",
					"0001180614000058", "0001180614000055", "00011806140000B4" };
			String[] macs2 = { "0001180614000011", "00011806140000A0", "00011806140000A6", "000118061400007A" };
			for (String s : macs) {
				DeviceSensor sensor = deviceSensorService.findByMac(s);
				// 视频停满三分钟
				if (!"".equals(sensor.getCph()) && 1 == sensor.getAvailable()) {
					Date date = sensor.getLastSeenTime();
					Date now = new Date();
					long sub_time = (now.getTime() - date.getTime()) / 1000;
					if (sub_time > 180 && StringUtil.isBlank(sensor.getBluetooth())) {// 视频停满三分钟
						sensor.setBluetooth("ASD");
						deviceSensorService.update(sensor);
						HttpUtil.get(VEDIO_URL + "?mac=" + sensor.getMac() + "&eventTime=" + sensor.getVedioTime()
								+ "&status=2");
						SendUtils.send(sensor.getHappenTime(), sensor.getMac(), "", "", "", sensor.getVedioTime(),
								sensor.getCId(), sensor.getCph(), sensor.getCpColor(), "2", sensor.getPicLink());
					}
				}
			}
			for (String s : macs2) {
				DeviceSensor sensor = deviceSensorService.findByMac(s);
				// 地磁三分钟后仍收不到视频信息录制一段
				if ("".equals(sensor.getCph()) && "".equals(sensor.getPicLink())) {
					Date date = sensor.getHappenTime();
					Date now = new Date();
					if ((now.getTime() - date.getTime()) > 180000) {// 3分钟
						String eventTime = sdf.format(date);
						HttpUtil.get(VEDIO_URL + "?mac=" + sensor.getMac() + "&eventTime=" + eventTime + "&status="
								+ sensor.getAvailable());
						LogSensorStatus log = new LogSensorStatus();
						String picLink = VEDIO_URL_IMG + "" + sdf2.format(date) + "/" + sensor.getMac() + "_"
								+ eventTime;
						log.setMac(sensor.getMac());
						log.setChangeTime(date);
						log.setAvailable(sensor.getAvailable());
						log.setFailTimes(0);
						log.setSendStatus(0);
						log.setAreaId(sensor.getAreaId());
						log.setCreateTime(new Date());
						log.setCameraId("");
						log.setCpColor("白色");
						log.setCph(getCph());
						log.setPicLink(picLink);
						log.setType(2);
						log.setDescription(sensor.getDesc());
						log.setStatus(String.valueOf(sensor.getAvailable()));
						sensor.setPicLink(picLink);
						deviceSensorService.update(sensor);
						logSensorLogService.saveStatusLog(log);
						boolean res = SendUtils.send(sensor.getHappenTime(), sensor.getMac(),
								String.valueOf(sensor.getAvailable()), "", sensor.getSensorTime(),
								sensor.getSensorTime(), sensor.getCId(), log.getCph(), log.getCpColor(),
								log.getStatus(), log.getPicLink());
						log = logSensorLogService.getStatusLog(log.getId());
						if (res) {
							log.setSendStatus(1);
							log.setSendTime(new Date());
							logSensorLogService.updateStatus(log);
						} else {
							log.setFailTimes(log.getFailTimes() + 1);
							logSensorLogService.updateStatus(log);
						}
					}
				}
				// 视频停满三分钟
				if (!"".equals(sensor.getCph()) && 1 == sensor.getAvailable()) {
					Date date = sensor.getLastSeenTime();
					Date now = new Date();
					long sub_time = (now.getTime() - date.getTime()) / 1000;
					if (sub_time > 180 && StringUtil.isBlank(sensor.getBluetooth())) {// 视频停满三分钟
						sensor.setBluetooth("ASD");
						deviceSensorService.update(sensor);
						HttpUtil.get(VEDIO_URL + "?mac=" + sensor.getMac() + "&eventTime=" + sensor.getVedioTime()
								+ "&status=2");
						SendUtils.send(sensor.getHappenTime(), sensor.getMac(), String.valueOf(sensor.getAvailable()),
								"", sensor.getSensorTime(), sensor.getVedioTime(), sensor.getCId(), sensor.getCph(),
								sensor.getCpColor(), "2", sensor.getPicLink());
					}
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	private static String getCph() {
		String str2 = "ABCDEFGHJKLPQS";
		String str = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int number = random.nextInt(34);
			sb.append(str.charAt(number));
		}
		return "沪" + str2.charAt(random.nextInt(14)) + sb.toString() + "警";
	}

}