package service.basicFunctions.guodong;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.guodong.GuodongSensorDao;
import database.models.guodong.GuodongJob;
import database.models.guodong.GuodongSensor;

@Service
public class GuodongSensorService {
	
	private static final Logger logger = LoggerFactory.getLogger(GuodongSensorService.class);
	
	@Autowired
	private GuodongSensorDao guodongSensorDao;
	@Autowired
	private GuodongJobService guodongJobService;
	
	public List<GuodongSensor> findAll(){
		return guodongSensorDao.findAll();
	}
	
	public void update(String jsonStr){
		try {
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			String devEUI = jsonObject.getString("devEUI");
			if(devEUI!=null&&!"".equals(devEUI)){
				String data = jsonObject.getString("data");
				String time_s = jsonObject.getString("time_s");
				char[] c = data.toCharArray();
				String s = "";
				if(c!=null&&c.length>0){
					for(int i = 0;i<c.length;i++){
						if(i>5)
							s += String.valueOf(c[i]);
					}
				}
				if(s.equals("D20100")){
					GuodongJob guodongJob = guodongJobService.findByDevEui(devEUI);
					if(0==guodongJob.getStatus()){
						guodongJob.setTime_s(time_s);
						guodongJob.setStatus(1);
						guodongJobService.update(guodongJob);
					}
				}else{
					GuodongSensor guodongSensor = guodongSensorDao.findByEUI(devEUI);
					if(guodongSensor!=null){
						guodongSensor.setData(data);
						guodongSensor.setTime_s(time_s);
						guodongSensorDao.update(guodongSensor);
					}else{
						guodongSensor = new GuodongSensor();
						guodongSensor.setData(data);
						guodongSensor.setCreateTime(new Date());
						guodongSensor.setDevEUI(devEUI);
						guodongSensor.setTime_s(time_s);
						guodongSensorDao.save(guodongSensor);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
	}
	
}
