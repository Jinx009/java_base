package service.basicFunctions.log;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.log.LogSensorHeartDao;
import database.basicFunctions.dao.log.LogSensorStatusDao;
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
	
}
