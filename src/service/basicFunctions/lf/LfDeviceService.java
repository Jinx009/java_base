package service.basicFunctions.lf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.lf.LfDeviceDao;
import database.models.lf.LfDevice;

@Service
public class LfDeviceService {

	@Autowired
	private LfDeviceDao lfDeviceDao;
	
	public LfDevice findById(String id){
		return lfDeviceDao.findById(id);
	}
	
	public void save(LfDevice device){
		lfDeviceDao.save(device);
	}

	public List<LfDevice> findAll() {
		return lfDeviceDao.findAll();
	}

	public void update(LfDevice lfDevice) {
		lfDeviceDao.update(lfDevice);
	}
	
}
