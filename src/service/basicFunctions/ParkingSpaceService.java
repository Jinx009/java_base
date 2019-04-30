package service.basicFunctions;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkingSpaceDao;
import database.common.PageDataList;
import database.models.ParkingSpace;

@Service
public class ParkingSpaceService {

	@Autowired
	private ParkingSpaceDao parkingSpaceDao;
	
	
	public void save(String name,Integer streetId,Integer status,Integer cameraId,String cameraNumber,Date happenTime){
		ParkingSpace parkingSpace = new ParkingSpace();
		parkingSpace.setStreetId(streetId);
		parkingSpace.setStatus(status);
		parkingSpace.setCameraId(cameraId);
		parkingSpace.setCameraNumber(cameraNumber);
		parkingSpace.setHappenTime(happenTime);
		parkingSpace.setCreateTime(new Date());
		parkingSpaceDao.save(parkingSpace);
	}
	
	public void update(Integer id,String name,Integer streetId,Integer status,Integer cameraId,String cameraNumber,Date happenTime){
		ParkingSpace parkingSpace = parkingSpaceDao.find(id);
		parkingSpace.setStreetId(streetId);
		parkingSpace.setStatus(status);
		parkingSpace.setCameraId(cameraId);
		parkingSpace.setCameraNumber(cameraNumber);
		parkingSpace.setHappenTime(happenTime);
		parkingSpaceDao.save(parkingSpace);
	}
	
	public void delete(Integer id){
		parkingSpaceDao.delete(id);
	}
	
	public PageDataList<ParkingSpace> findByPage(int p){
		return parkingSpaceDao.findByPage(p);
	}

	public ParkingSpace find(Integer id) {
		return parkingSpaceDao.find(id);
	}
	
	public ParkingSpace getByCameraNameAndParkNumber(String cameraName,String parkNumber){
		return parkingSpaceDao.getByCameraNameAndParkNumber(cameraName,parkNumber);
	}

	public void update(ParkingSpace parkingSpace) {
		parkingSpaceDao.update(parkingSpace);
	}
	
}
