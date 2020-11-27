package main.entry.webapp.task;

import java.text.SimpleDateFormat;
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
import database.model.GnssRtkDevice;
import database.model.GnssRtkLog;
import database.model.GnssRtkNumLog;
import main.entry.webapp.mongo.MongoUtil;
import service.GnssRtkDeviceService;
import service.GnssRtkLogService;
import service.GnssRtkNumLogService;

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
	
	@Scheduled(cron = "0 */20 * * * ?")//20分钟处理一次
	public void init(){
		GnssRtkDevice rtk = gnssRtkDeviceService.findByNewTime();
		if(rtk!=null&&StringUtil.isBlank(rtk.getUpdatetime())){
			List<GnssRtkLog> list = MongoUtil.select();
			logger.warn("select:{}",JSONObject.toJSONString(list));
			if(list!=null&&!list.isEmpty()){
				save(list,"");
			}
		}else{
			List<GnssRtkLog> list = MongoUtil.select(rtk.getUpdatetime());
			logger.warn("select updatetime:{}",JSONObject.toJSONString(list));
			if(list!=null&&!list.isEmpty()){
				save(list,rtk.getUpdatetime());
			}
		}
	}
	
	private void save(List<GnssRtkLog> list,String updatetime){
		for(GnssRtkLog log : list){
			if(!updatetime.equals(log.getUpdatetime())){
				log.setCreateTime(new Date());
				gnssRtkLogService.save(log);
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
				device.setUpdatetime(log.getUpdatetime());
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
				device.setUpdatetime(log.getUpdatetime());
				device.setWindow(log.getWindow());
				gnssRtkDeviceService.update(device);
			}
		}
	}
	
}
