package main.entry.webapp.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import database.model.GnssRtkDevice;
import database.model.GnssRtkLog;
import main.entry.webapp.mongo.MongoUtil;
import service.GnssRtkDeviceService;
import service.GnssRtkLogService;

@Component
@Lazy(value=false)
public class RtkTask {

	private static final Logger logger = LoggerFactory.getLogger(RtkTask.class);
	
	@Autowired
	private GnssRtkDeviceService gnssRtkDeviceService;
	@Autowired
	private GnssRtkLogService gnssRtkLogService;
	
	@Scheduled(cron = "0 */20 * * * ?")//20分钟处理一次
	public void init(){
		GnssRtkDevice rtk = gnssRtkDeviceService.findByNewTime();
		if(rtk==null){
			List<GnssRtkLog> list = MongoUtil.select();
			logger.warn("select:{}",JSONObject.toJSONString(list));
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
				device.setDatetime(log.getDatetime());
				device.setUpdatetime(log.getUpdatetime());
				gnssRtkDeviceService.update(device);
			}
		}
	}
	
}
