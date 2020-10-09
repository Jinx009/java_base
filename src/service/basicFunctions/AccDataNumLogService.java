package service.basicFunctions;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccDataNumLogDao;
import database.dao.IoTCloudEventLogDao;
import database.models.AccDataNumLog;
import database.models.IoTCloudEventLog;

@Service
public class AccDataNumLogService {

	@Autowired
	private AccDataNumLogDao accDataNumLogDao;
	@Autowired
	private IoTCloudEventLogDao ioTCloudEventLogDao;
	
	private static final String HEART = "心跳";
	private static final String WARN = "报警";

	public void save(String data,int type) {
		if(3==type) {
			String mac = data.substring(0, 16);
			String cmd = data.substring(20, 22);
			if("68".equals(cmd)) {
				cmd = HEART;
			}
			if("69".equals(cmd)) {
				cmd = WARN;
			}
			AccDataNumLog log = accDataNumLogDao.findByCurrentDate(mac,cmd); 
			int num = log.getNum()+1;
			log.setNum(num);
			accDataNumLogDao.update(log);
			AccDataNumLog log2 = accDataNumLogDao.findByCurrent(mac,cmd); 
			int num2 = log2.getNum()+1;
			log2.setNum(num2);
			accDataNumLogDao.update(log2);
			if(WARN.equals(cmd)) {
				if(num==150) {
					IoTCloudEventLog event = new IoTCloudEventLog();
					event.setCreateTime(new Date());
					event.setFatherType("WARN");
					event.setMac(mac);
					event.setType("DATANUM");
					event.setDescription("当日报警条数已经达到："+num+"条");
					ioTCloudEventLogDao.save(event);
				}
				if(num2==50) {
					IoTCloudEventLog event = new IoTCloudEventLog();
					event.setCreateTime(new Date());
					event.setFatherType("WARN");
					event.setMac(mac);
					event.setType("DATANUM");
					event.setDescription("当日单小时报警条数已经达到："+num2+"条");
					ioTCloudEventLogDao.save(event);
				}
			}
		}
	}
	
	
	
}
