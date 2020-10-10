package service.basicFunctions;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccVersionLogDao;
import database.dao.IoTCloudEventLogDao;
import database.models.AccVersionLog;
import database.models.IoTCloudEventLog;

@Service
public class AccVersionLogService {


	@Autowired
	private AccVersionLogDao accVersionLogDao;
	@Autowired
	private IoTCloudEventLogDao ioTCloudEventLogDao;
	
	public void save(AccVersionLog accVersionLog) {
		accVersionLogDao.save(accVersionLog);
	}
	
	public void update(AccVersionLog accVersionLog) {
		accVersionLogDao.update(accVersionLog);
	}

	public void save(String data,int type) {
		if(type==3) {
			String mac = data.substring(0, 16);
			String cmd = data.substring(20, 22);
			if("68".equals(cmd)) {
				String hard = convertHexToString(data.substring(82, 94));
				String soft = convertHexToString(data.substring(94, 106));
				AccVersionLog log = accVersionLogDao.findByMac(mac);
				if(log == null) {
					log = new AccVersionLog();
					log.setCreateTime(new Date());
					log.setHard(hard);
					log.setSoft(soft);
					log.setMac(mac);
					accVersionLogDao.save(log);
					IoTCloudEventLog event = new IoTCloudEventLog();
					event.setCreateTime(new Date());
					event.setMac(mac);
					event.setFatherType("EVENT");
					event.setType("VERSION");
					String s = "首次接入，硬件版本："+hard+",软件版本："+soft;
					event.setDescription(s);
					ioTCloudEventLogDao.save(event);
				}else {
					log = accVersionLogDao.findByMacAndVersion(hard,soft,mac);
					if(log == null) {
						log = new AccVersionLog();
						log.setCreateTime(new Date());
						log.setHard(hard);
						log.setSoft(soft);
						log.setMac(mac);
						accVersionLogDao.save(log);
						IoTCloudEventLog event = new IoTCloudEventLog();
						event.setCreateTime(new Date());
						event.setMac(mac);
						event.setFatherType("EVENT");
						event.setType("VERSION");
						String s = "版本升级，硬件版本："+hard+",软件版本："+soft;//
						event.setDescription(s);
						ioTCloudEventLogDao.save(event);
					}
				}
			}
		}
	}
	
	
	public String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}
	
}
