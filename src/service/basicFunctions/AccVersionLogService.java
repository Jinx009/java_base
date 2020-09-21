package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccVersionLogDao;
import database.models.AccVersionLog;

@Service
public class AccVersionLogService {


	@Autowired
	private AccVersionLogDao accVersionLogDao;
	
	public void save(AccVersionLog accVersionLog) {
		accVersionLogDao.save(accVersionLog);
	}
	
	public void update(AccVersionLog accVersionLog) {
		accVersionLogDao.update(accVersionLog);
	}
	
	
}
