package service.basicFunctions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.IoTCloudEventLogDao;
import database.models.IoTCloudEventLog;
import database.models.vo.TModel;

@Service
public class IoTCloudEventLogService {
	
	private static final Logger log = LoggerFactory.getLogger(IoTCloudEventLogService.class);

	@Autowired
	private IoTCloudEventLogDao ioTCloudEventLogDao;
	@Autowired
	private AccDataLogService accDataLogService;
	@Autowired
	private AccAngleLogService accAngleLogService;
	@Autowired
	private AccDataNumLogService accDataNumLogService;
	@Autowired
	private AccVersionLogService accVersionLogService;
	
	
	public void save(IoTCloudEventLog ioTCloudEventLog) {
		ioTCloudEventLogDao.save(ioTCloudEventLog);
	}
	
	public void update(IoTCloudEventLog ioTCloudEventLog) {
		ioTCloudEventLogDao.update(ioTCloudEventLog);
	}

	public void save(TModel tModel, int i) {
		try {
			accDataLogService.save(tModel.getData(), 3);
			accDataNumLogService.save(tModel.getData(),3);
			accVersionLogService.save(tModel.getData(),3);
			accAngleLogService.save(tModel.getData(),3);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
	}

	public void saveChange(String mac, String cmd,String x, String y, String z, String acc_x, String acc_y,String acc_z) {
		accDataLogService.saveChange(mac,cmd,x,y,z,acc_x,acc_y,acc_z);
	}
	
}
