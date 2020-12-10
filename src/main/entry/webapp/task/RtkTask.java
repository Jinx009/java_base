package main.entry.webapp.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.model.GnssRtkBatteryLog;
import database.model.GnssRtkDevice;
import database.model.GnssRtkLog;
import database.model.GnssRtkNumLog;
import main.entry.webapp.mongo.MongoUtil;
import service.GnssRtkBatteryLogService;
import service.GnssRtkDeviceService;
import service.GnssRtkLogService;
import service.GnssRtkNumLogService;
import utils.HttpUtils;

@Component
@Lazy(value=false)
public class RtkTask {

	private static final Logger logger = LoggerFactory.getLogger(RtkTask.class);
	
	@Autowired
	private GnssRtkDeviceService gnssRtkDeviceService;
	@Autowired
	private GnssRtkLogService gnssRtkLogService;
	@Autowired
	private GnssRtkNumLogService gnssRtkNumLogService;
	@Autowired
	private GnssRtkBatteryLogService gnssRtkBatteryLogService;
	
	
	
	
	@Scheduled(cron = "0 1 0 * * ?") // 每天晚上0点01分创建新文件夹
	public void chmod() {
		try {
			List<GnssRtkDevice> list = gnssRtkDeviceService.findAll();
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(d);
			for(GnssRtkDevice device: list) {
				for(int i = 0;i<24;i++) {
					GnssRtkNumLog numLog = gnssRtkNumLogService.find(date, device.getMac(), i);
					if(numLog==null) {
						numLog = new GnssRtkNumLog();
						numLog.setCreateTime(new Date());
						numLog.setDate(date);
						numLog.setStartHour(i);
						numLog.setEndHour((i+1));
						numLog.setNum(0);
						numLog.setMac(device.getMac());
						numLog.setType("RTCM");
						gnssRtkNumLogService.save(numLog);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
	}
	
//	@Scheduled(cron = "0 0 0/1 * * ?")//1小时
	public void sun(){
		List<GnssRtkDevice> rtk = gnssRtkDeviceService.findAll();
		String json = HttpUtils.get("http://Xmnengjia.com/solarLamp/api/external/successToken?username=上海展为&password=112233");
		JSONObject obj = JSONObject.parseObject(json);
		String token = obj.getString("data");
		for(GnssRtkDevice device:rtk) {
			if(StringUtil.isNotBlank(device.getImei())) {
				String objJson = HttpUtils.postSun("http://Xmnengjia.com/solarLamp/api/external/moduleStatus?imei="+device.getImei(), token);
				JSONObject jsonObject = JSONObject.parseObject(objJson);
				String battrey = jsonObject.getString("data");
				GnssRtkBatteryLog log = JSONObject.parseObject(battrey,GnssRtkBatteryLog.class);
				log.setCreate_time(new Date());
				log.setImei(device.getImei());
				gnssRtkBatteryLogService.save(log);
			}
		}
	}
	
	@Scheduled(cron = "0 0 0/1 * * ?")//1小时
	public void job() throws ParseException{
		List<GnssRtkDevice> rtk = gnssRtkDeviceService.findAll();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
		int start = nowHour-3;
		int end = nowHour-2;
		for(GnssRtkDevice device:rtk) {
			if(1==device.getSwitchType()) {
				List<GnssRtkLog> list = gnssRtkLogService.findByMacAndDate(device.getMac(),date,start,end,0);
				if(list==null) {
					GnssRtkLog log = new GnssRtkLog();
					log.setRovertag(device.getMac());
					log.setCreateTime(new Date());
					log.setDatetime(String.valueOf(sdf2.parse(sdf.format(date)+" "+getHour(start)+":00:00").getTime()));
					log.setType(0);
					gnssRtkLogService.saveStatus(log, 0);
				}
				List<GnssRtkLog> list2 = gnssRtkLogService.findByMacAndDate(device.getMac(),date,start,end,1);
				if(list2==null) {
					GnssRtkLog log = new GnssRtkLog();
					log.setRovertag(device.getMac());
					log.setCreateTime(new Date());
					log.setDatetime(String.valueOf(sdf2.parse(sdf.format(date)+" "+getHour(start)+":00:00").getTime()));
					log.setType(1);
					gnssRtkLogService.saveStatus(log, 0);
				}
			}
		}
	}
	
	private String getHour(int nowHour) {
       nowHour = nowHour -1;
		if(nowHour<10) {
			return "0"+nowHour;
		}
		return String.valueOf(nowHour);
	}

	@Scheduled(cron = "0 */5 * * * ?")//20分钟处理一次
	public void init(){
		GnssRtkDevice rtk = gnssRtkDeviceService.findByNewTime();
		if(rtk!=null&&StringUtil.isBlank(rtk.getUpdatetime())){
			List<GnssRtkLog> list = MongoUtil.select();
//			logger.warn("select:{}",JSONObject.toJSONString(list));
			if(list!=null&&!list.isEmpty()){
				save(list,"");
			}
		}else{
			List<GnssRtkLog> list = MongoUtil.select(rtk.getUpdatetime());
//			logger.warn("select updatetime:{}",JSONObject.toJSONString(list));
			if(list!=null&&!list.isEmpty()){
				save(list,rtk.getUpdatetime());
			}
		}
		GnssRtkDevice rtk2 = gnssRtkDeviceService.findByNew2Time();
		if(rtk2!=null&&StringUtil.isBlank(rtk2.getUpdatetime2())){
			List<GnssRtkLog> list = MongoUtil.selectdailyresult();
//			logger.warn("select2:{}",JSONObject.toJSONString(list));
			if(list!=null&&!list.isEmpty()){
				save(list,"",1);
			}
		}else{
			List<GnssRtkLog> list = MongoUtil.selectdailyresult(rtk2.getUpdatetime2());
//			logger.warn("select updatetime2:{}",JSONObject.toJSONString(list));
			if(list!=null&&!list.isEmpty()){
				save(list,rtk2.getUpdatetime2(),1);
			}
		}
	}
	
	private void save(List<GnssRtkLog> list, String updatetime2, int i) {
		for(GnssRtkLog log : list){
			if(!updatetime2.equals(log.getDatetime())){
				log.setCreateTime(new Date());
				gnssRtkLogService.save(log,i);
			}
			GnssRtkDevice device = gnssRtkDeviceService.findByRoverTag(log.getRovertag());
			if(device==null){
				device = new GnssRtkDevice();
				device.setBasetag(log.getBasetag());
				device.setCoverrate(log.getCoverrate());
				device.setCreateTime(new Date());
				device.setDatetime(log.getDatetime());
				device.setEast(log.getEast());
				device.setFixrate(log.getFixrate());
				device.setHeight(log.getHeight());
				device.setLat(log.getLat());
				device.setLng(log.getLng());
				device.setNorth(log.getNorth());
				device.setQuality(log.getQuality());
				device.setRovertag(log.getRovertag());
				device.setSatellites(log.getSatellites());
				device.setUp(log.getUp());
				device.setWindow(log.getWindow());
				device.setUpdatetime2(log.getDatetime());
				gnssRtkDeviceService.save(device);
			}else{
				device.setBasetag(log.getBasetag());
				device.setCoverrate(log.getCoverrate());
				device.setDatetime(log.getDatetime());
				device.setEast(log.getEast());
				device.setFixrate(log.getFixrate());
				device.setHeight(log.getHeight());
				device.setLat(log.getLat());
				device.setLng(log.getLng());
				device.setNorth(log.getNorth());
				device.setQuality(log.getQuality());
				device.setRovertag(log.getRovertag());
				device.setSatellites(log.getSatellites());
				device.setUp(log.getUp());
				device.setUpdatetime2(log.getDatetime());
				device.setWindow(log.getWindow());
				gnssRtkDeviceService.update(device);
			}
		}
	}

	private void save(List<GnssRtkLog> list,String updatetime){
		for(GnssRtkLog log : list){
			if(!updatetime.equals(log.getDatetime())){
				log.setCreateTime(new Date());
				gnssRtkLogService.save(log,0);
			}
			GnssRtkDevice device = gnssRtkDeviceService.findByRoverTag(log.getRovertag());
			if(device==null){
				device = new GnssRtkDevice();
				device.setBasetag(log.getBasetag());
				device.setCoverrate(log.getCoverrate());
				device.setCreateTime(new Date());
				device.setDatetime(log.getDatetime());
				device.setEast(log.getEast());
				device.setFixrate(log.getFixrate());
				device.setHeight(log.getHeight());
				device.setLat(log.getLat());
				device.setLng(log.getLng());
				device.setNorth(log.getNorth());
				device.setQuality(log.getQuality());
				device.setRovertag(log.getRovertag());
				device.setSatellites(log.getSatellites());
				device.setUp(log.getUp());
				device.setUpdatetime(log.getDatetime());
				device.setWindow(log.getWindow());
				gnssRtkDeviceService.save(device);
			}else{
				device.setBasetag(log.getBasetag());
				device.setCoverrate(log.getCoverrate());
				device.setDatetime(log.getDatetime());
				device.setEast(log.getEast());
				device.setFixrate(log.getFixrate());
				device.setHeight(log.getHeight());
				device.setLat(log.getLat());
				device.setLng(log.getLng());
				device.setNorth(log.getNorth());
				device.setQuality(log.getQuality());
				device.setRovertag(log.getRovertag());
				device.setSatellites(log.getSatellites());
				device.setUp(log.getUp());
				device.setUpdatetime(log.getDatetime());
				device.setWindow(log.getWindow());
				gnssRtkDeviceService.update(device);
			}
		}
	}
	
}
