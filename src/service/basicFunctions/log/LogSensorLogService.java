package service.basicFunctions.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.log.LogSensorHeartDao;
import database.basicFunctions.dao.log.LogSensorStatusDao;
import database.models.device.DeviceSensor;
import database.models.log.LogSensorHeart;
import database.models.log.LogSensorStatus;
import service.basicFunctions.BaseService;
import utils.WuhanSendUtils;
import utils.bearhunting.BearHuntingDataUtils;
import utils.bearhunting.KeyUtils;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class LogSensorLogService extends BaseService {

	private static final Logger log = LoggerFactory.getLogger(LogSensorLogService.class);

	@Autowired
	private LogSensorHeartDao logSensorHeartDao;
	@Autowired
	private LogSensorStatusDao logSensorStatusDao;

	public List<String> findAlive() {
		return logSensorHeartDao.findAlive();
	}

	public String findDate(String mac) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<LogSensorHeart> list = logSensorHeartDao.findByMacAndDate(mac, sdf.format(date));
			if (list != null && !list.isEmpty()) {
				return sdf2.format(list.get(0).getCreateTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 地磁心跳列表
	 * 
	 * @param params
	 * @return
	 */
	public Resp<?> heart(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String dateStr = jsonObject.getString(BaseConstant.DATE_STR);
			String mac = jsonObject.getString(BaseConstant.MAC);
			resp = new Resp<>(logSensorHeartDao.findByMacAndDate(mac, dateStr));
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}

	/**
	 * 地磁心跳列表
	 * 
	 * @param params
	 * @return
	 */
	public Resp<?> status(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String dateStr = jsonObject.getString(BaseConstant.DATE_STR);
			String mac = jsonObject.getString(BaseConstant.MAC);
			Integer areaId = jsonObject.getInteger(BaseConstant.AREA_ID);
			Integer size = jsonObject.getInteger("size");
			resp = new Resp<>(logSensorStatusDao.findUse(1, areaId, mac, size, dateStr));
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}

	public LogSensorHeart findByInfoMac(String mac) {
		return logSensorHeartDao.findNewByMac(mac);
	}

	public List<LogSensorHeart> findList(String mac, String date1, String date2, String time1, String time2) {
		return logSensorHeartDao.findList(mac, date1, date2, time1, time2);
	}

	public void saveOperationLog(DeviceSensor sensor) {
		LogSensorStatus sensorOperationLog = new LogSensorStatus();
		sensorOperationLog.setAvailable(sensor.getAvailable());
		sensorOperationLog.setCreateTime(new Date());
		sensorOperationLog.setChangeTime(new Date());
		sensorOperationLog.setAreaId(sensor.getAreaId());
		sensorOperationLog.setMac(sensor.getMac());
		sensorOperationLog.setDescription(sensor.getDesc());
		sensorOperationLog.setFailTimes(0);
		sensorOperationLog.setSendStatus(0);
		sensorOperationLog.setSendTime(new Date());
		sensorOperationLog.setCph(sensor.getCph());
		logSensorStatusDao.save(sensorOperationLog);
		sensorOperationLog = logSensorStatusDao.find(sensorOperationLog.getId());
		// 武汉
		if (sensorOperationLog.getAreaId() != null && 64 == sensorOperationLog.getAreaId()) {
			String status = WuhanSendUtils.sendStatus(sensorOperationLog, sensor);
			if("1".equals(status)){
				sensorOperationLog.setSendStatus(1);
				sensorOperationLog.setSendTime(new Date());
				logSensorStatusDao.update(sensorOperationLog);
			}
		}
		//周浦界浜村
		if (sensorOperationLog.getAreaId() != null && 65 == sensorOperationLog.getAreaId()) {
			String status = BearHuntingDataUtils.sendStatus(KeyUtils.STATUS_FIRE_URL,sensorOperationLog);
			Integer code = JSON.parseObject(status).getInteger("status");
			if(1==code){
				sensorOperationLog.setSendStatus(1);
				sensorOperationLog.setSendTime(new Date());
				logSensorStatusDao.update(sensorOperationLog);
			}
		}
		//周浦菱翔苑
		if (sensorOperationLog.getAreaId() != null && 34 == sensorOperationLog.getAreaId()) {
			String status = BearHuntingDataUtils.sendStatusFire(KeyUtils.STATUS_FIRE_URL2,sensorOperationLog,"zhanway");
			Integer code = JSON.parseObject(status).getInteger("status");
			if(1==code){
				sensorOperationLog.setSendStatus(1);
				sensorOperationLog.setSendTime(new Date());
				logSensorStatusDao.update(sensorOperationLog);
			}
		}
		//潮州
//		if (sensorOperationLog.getAreaId() != null && 1 == sensorOperationLog.getAreaId()) {
//			int status = JSONObject.parseObject(ChaozhouSendUtils.sendStatus(sensorOperationLog, "00163e0cec7c")).getIntValue("state");
//			if(0==status){
//				sensorOperationLog.setSendStatus(1);
//				sensorOperationLog.setSendTime(new Date());
//				logSensorStatusDao.update(sensorOperationLog);
//			}
//		}
//宝信		
//		if (sensorOperationLog.getAreaId() != null && 1 == sensorOperationLog.getAreaId()) {
//			boolean status = SendUtils.send(sensor.getHappenTime(), sensor.getMac(), String.valueOf(sensor.getAvailable()), "", sensor.getSensorTime(), "", "", "", "", "", "");
//			if(status){
//				sensorOperationLog.setSendStatus(1);
//				sensorOperationLog.setSendTime(new Date());
//				logSensorStatusDao.update(sensorOperationLog);
//			}
//		}

	}

	public void saveHeart(LogSensorHeart deviceLog) {
		logSensorHeartDao.save(deviceLog);
	}

	public void saveStatusLog(LogSensorStatus log2) {
		logSensorStatusDao.save(log2);
	}

	public void updateStatus(LogSensorStatus log2) {
		logSensorStatusDao.update(log2);
	}

	public LogSensorStatus getStatusLog(Integer id) {
		return logSensorStatusDao.find(id);
	}

}
