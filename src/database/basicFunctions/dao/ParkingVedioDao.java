package database.basicFunctions.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.ParkingVedio;

@Repository
public class ParkingVedioDao extends BaseDao<ParkingVedio>{


	public List<ParkingVedio> findByStatus(Integer status) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("status", status);
		List<ParkingVedio> list = findByCriteria(param);
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}
	
}
