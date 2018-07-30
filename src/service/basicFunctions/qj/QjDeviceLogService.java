package service.basicFunctions.qj;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.qj.QjDeviceLogDao;
import database.models.qj.QjDeviceLog;

@Service
public class QjDeviceLogService {

	@Autowired
	private QjDeviceLogDao qjDeviceLogDao;
	
	public void save(QjDeviceLog q){
		q.setCreateTime(new Date());
		qjDeviceLogDao.save(q);
	}
	
}
