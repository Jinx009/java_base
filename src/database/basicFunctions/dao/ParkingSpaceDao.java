package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.ParkingSpace;
import utils.model.BaseConstant;

@Repository
public class ParkingSpaceDao extends BaseDao<ParkingSpace>{

	public PageDataList<ParkingSpace> findByPage(int p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public ParkingSpace getByCameraNameAndParkNumber(String cameraName,String parkNumber){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("cameraNumber", cameraName);
		queryParam.addParam("parkNumber", parkNumber);
		return findByCriteriaForUnique(queryParam);
	}

	public ParkingSpace findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}
	
}
