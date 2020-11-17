package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssMsgLogDao;
import database.model.GnssMsgLog;

@Service
public class GnssMsgLogService {

	@Autowired
	private GnssMsgLogDao gnssMsgLogDao;
	
	public void save(String topic,String content) {
		GnssMsgLog log = new GnssMsgLog();
		log.setContent(content);
		log.setLength(content.length());
		log.setTopic(topic);
		log.setCreateTime(new Date());
		gnssMsgLogDao.save(log);
	}
	
}
