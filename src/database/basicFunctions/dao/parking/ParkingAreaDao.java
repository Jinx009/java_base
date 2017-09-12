package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.parking.ParkingArea;

@Repository
public class ParkingAreaDao extends BaseDao<ParkingArea>{

	public List<ParkingArea> findUseAll() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",1);
		return findByCriteria(queryParam);
	}

	
	
}
