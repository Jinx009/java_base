package service.basicFunctions.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.log.LogSensorHeartDao;
import database.basicFunctions.dao.log.LogSensorStatusDao;
import database.models.log.LogSensorHeart;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class LogSensorLogService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(LogSensorLogService.class);

	@Autowired
	private LogSensorHeartDao logSensorHeartDao;
	@Autowired
	private LogSensorStatusDao logSensorStatusDao;
	
	public List<String> findAlive(){
		return logSensorHeartDao.findAlive();
	}
	
	public String findDate(String mac){
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			List<LogSensorHeart> list = logSensorHeartDao.findByMacAndDate(mac,sdf.format(date));
			if(list!=null&&!list.isEmpty()){
				return sdf2.format(list.get(0).getCreateTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 地磁心跳列表
	 * @param params
	 * @return
	 */
	public Resp<?> heart(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String dateStr = jsonObject.getString(BaseConstant.DATE_STR);
			String mac = jsonObject.getString(BaseConstant.MAC);
			resp = new Resp<>(logSensorHeartDao.findByMacAndDate(mac,dateStr));
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}	
	
	/**
	 * 地磁心跳列表
	 * @param params
	 * @return
	 */
	public Resp<?> status(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String dateStr = jsonObject.getString(BaseConstant.DATE_STR);
			String mac = jsonObject.getString(BaseConstant.MAC);
			Integer areaId = jsonObject.getInteger(BaseConstant.AREA_ID);
			Integer size = jsonObject.getInteger("size");
			resp = new Resp<>(logSensorStatusDao.findUse(1,areaId, mac, size,dateStr));
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}

	public LogSensorHeart findByInfoMac(String mac) {
		return logSensorHeartDao.findNewByMac(mac);
	}

	public List<LogSensorHeart> findList(String mac, String date1, String date2, String time1, String time2) {
		return logSensorHeartDao.findList(mac,date1,date2,time1,time2);
	}	
	
}
