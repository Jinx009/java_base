package service.basicFunctions.lf;


import database.basicFunctions.dao.lf.LfLogDao;
import database.models.lf.LfLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LfLogService {

	@Autowired
	private LfLogDao lfLogDao;
	
	
	public void save(LfLog lfLog){
		lfLogDao.save(lfLog);
	}
	
}
