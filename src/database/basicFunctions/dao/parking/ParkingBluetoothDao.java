package database.basicFunctions.dao.parking;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.parking.ParkingBluetooth;

@Repository
public class ParkingBluetoothDao extends BaseDao<ParkingBluetooth>{

	public ParkingBluetooth findByUid(String uid){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("uuid", uid);
		List<ParkingBluetooth> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
