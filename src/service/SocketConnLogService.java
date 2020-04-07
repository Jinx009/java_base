package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.SocketConnLogDao;
import database.model.SocketConnLog;

@Service
public class SocketConnLogService {

	@Autowired
	private SocketConnLogDao socketConnLogDao;
	
	public void save(SocketConnLog socketConnLog) {
		socketConnLogDao.save(socketConnLog);
	}
	
	public List<SocketConnLog> find(){
		return socketConnLogDao.findAll2();
	}
	
	public PageDataList<SocketConnLog> find(Integer p){
		return socketConnLogDao.findByPage(p);
	}
	
}
