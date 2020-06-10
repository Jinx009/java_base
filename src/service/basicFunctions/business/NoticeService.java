package service.basicFunctions.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.business.NoticeDao;
import database.models.business.Notice;

@Service
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	
	public List<Notice> findAll(){
		return noticeDao.findAll();
	}
	
}
