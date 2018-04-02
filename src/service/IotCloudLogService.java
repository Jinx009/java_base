package service;

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
	
	public PageDataList<IotCloudLog> pageList(Integer p){
		return ioTCloudLogDao.findAll(p);
	}
	
	
}
