package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProLogDao;
import database.common.PageDataList;
import database.models.project.ProLog;

@Service
public class ProLogService {

	@Autowired
	private ProLogDao proLogDao;
	
	public void saveLog(String userName,String content) {
		ProLog proLog = new ProLog();
		proLog.setContent(content);
		proLog.setCreateTime(new Date());
		proLog.setUserName(userName);
		proLogDao.save(proLog);
	}
	
	public PageDataList<ProLog> homeList(Integer p){
		return proLogDao.homeList(p);
	}
	
	
}
