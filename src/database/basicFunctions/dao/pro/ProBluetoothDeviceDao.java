package database.basicFunctions.dao.pro;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.pro.ProBluetoothDevice;

@Repository
public class ProBluetoothDeviceDao extends BaseDao<ProBluetoothDevice>{

	public String findCarNo(String uuid) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("uuid",uuid);
		ProBluetoothDevice proBluetoothDevice = findByCriteriaForUnique(queryParam);
		if(proBluetoothDevice!=null){
			return proBluetoothDevice.getCarNo();
		}
		return null;
	}

	
	
}
