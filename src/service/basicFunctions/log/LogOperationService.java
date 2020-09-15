package service.basicFunctions.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.log.LogOperationDao;
import database.models.log.LogOperation;

@Service
public class LogOperationService {

	@Autowired
	private LogOperationDao logOperationDao;
	
	public void save(LogOperation logOperation) {
		logOperationDao.save(logOperation);
	}
	
	public List<LogOperation> findByMac(String mac){
		return logOperationDao.findByMac(mac);
	}
	
	public List<LogOperation> findAll(){
		return logOperationDao.findAll();
	}
	
}
