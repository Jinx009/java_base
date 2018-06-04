package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.IotCloudLogDao;
import database.models.IotCloudLog;

@Service
public class IotCloudLogService {

	@Autowired
	private IotCloudLogDao ioTCloudLogDao;
	
	public void save(IotCloudLog iotCloudLog){
		ioTCloudLogDao.save(iotCloudLog);
	}
	
	public PageDataList<IotCloudLog> pageList(Integer p, Integer type, String mac){
		return ioTCloudLogDao.findAll(p,type,mac);
	}

	public Object pageList(Integer p, Integer type, String mac, String fromSite) {
		return ioTCloudLogDao.findAll(p,type,mac,fromSite);
	}
	
	
}
