package service.basicFunctions.wenshidu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.wenshidu.WenshiduLogDao;
import database.models.wenshidu.WenshiduLog;

@Service
public class WenshiduLogService {
	
	@Autowired
	private WenshiduLogDao wenshiduLogDao;

	public void save(WenshiduLog log) {
		wenshiduLogDao.save(log);
	}

}
