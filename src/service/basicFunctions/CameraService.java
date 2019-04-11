package service.basicFunctions;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.CameraDao;
import database.common.PageDataList;
import database.models.Camera;
import utils.StringUtil;

@Service
public class CameraService {

	@Autowired
	private CameraDao cameraDao;
	
	public void save(String deviceNumber,Integer streetId,String streetName,String parkNumber,String deviceType){
		Camera camera = new Camera();
		camera.setCreateTime(new Date());
		camera.setStreetId(streetId);
		camera.setStreetName(streetName);
		camera.setParkNumber(parkNumber);
		camera.setDeviceType(deviceType);
		camera.setHappenTime(new Date());
		cameraDao.save(camera);
	}
	
	public void update(Integer id,String deviceNumber,Integer streetId,String streetName,String parkNumber,String deviceType){
		Camera camera = cameraDao.find(id);
		if(streetId!=null&&streetId!=0){
			camera.setStreetId(streetId);
			camera.setStreetName(streetName);
		}
		if(StringUtil.isNotBlank(parkNumber)){
			camera.setParkNumber(parkNumber);
		}
		if(StringUtil.isNotBlank(deviceType)){
			camera.setDeviceType(deviceType);
		}
		camera.setHappenTime(new Date());
		cameraDao.update(camera);
	}
	
	public void delete(Integer id){
		cameraDao.delete(id);
	}
	
	public PageDataList<Camera> findByPage(Integer streetId,int pageNum){
		return  cameraDao.findByPage(streetId,pageNum);
	}

	public Camera find(Integer id) {
		return cameraDao.find(id);
	}
	
}
