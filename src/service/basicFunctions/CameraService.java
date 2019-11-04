package service.basicFunctions;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.CameraDao;
import database.models.Camera;

@Service
public class CameraService {

	@Autowired
	private CameraDao cameraDao;
	
	public void update(Integer id,Date date){
		cameraDao.updateCamera(id, date);
	}
	
	public List<Camera> findAll(){
		return cameraDao.findAll();
	}
	
}
