package service.basicFunctions.qj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.qj.QjDeviceDao;
import database.models.qj.QjDevice;

@Service
public class QjDeviceService {

	@Autowired
	private QjDeviceDao qjDeviceDao;
	
	public List<QjDevice> findList(){
		return qjDeviceDao.findAll();
	}
	
	public QjDevice findBySn(String sn){
		return qjDeviceDao.findBySn(sn);
	}
	
	public void save(QjDevice qjDevice){
		qjDeviceDao.save(qjDevice);
	}

	public void update(QjDevice qjDevice) {
		qjDeviceDao.update(qjDevice);
	}
	
}
