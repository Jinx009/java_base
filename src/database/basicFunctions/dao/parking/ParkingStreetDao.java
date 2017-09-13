package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.parking.ParkingStreet;

@Repository
public class ParkingStreetDao extends BaseDao<ParkingStreet>{

	public List<ParkingStreet> findByAreaId(Integer areaId){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("areaId",areaId);
		return findByCriteria(queryParam);
	}

	public List<ParkingStreet> findUseAll() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",1);
		return findByCriteria(queryParam);
	}
	
}
