package database.basicFunctions.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.ParkingSpace;

@Repository
public class ParkingSpaceDao extends BaseDao<ParkingSpace>{

	public void updateSpace(Integer id,String plateNumber,Integer status,Date date){
		ParkingSpace space = find(id);
		space.setHappenTime(date);
		space.setPlateNumber(plateNumber);
		space.setStatus(status);
		update(space);
	}

	public List<ParkingSpace> findByCameraIndex(String cameraIndex) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("cameraIndex", cameraIndex);
		return findByCriteria(queryParam);
		
	}
	
}
