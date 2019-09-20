package service.basicFunctions.qj;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.qj.QjNoticeDao;
import database.models.qj.QjNotice;

@Service
public class QjNoticeService {

	@Autowired
	private QjNoticeDao qjNoticeDao;
	
	public void save(String mac){
		QjNotice qjNotice = new QjNotice();
		qjNotice.setMac(mac);
		qjNotice.setCreateTime(new Date());
		qjNoticeDao.save(qjNotice);
	}
	
	public List<QjNotice> findAll(){
		return qjNoticeDao.findAll();
	}
	
	public void del(Integer id){
		qjNoticeDao.delete(id);
	}
	
}
