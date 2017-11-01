package database.basicFunctions.dao.guodong;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.guodong.GuodongSensor;

@Repository
public class GuodongSensorDao extends BaseDao<GuodongSensor>{

	public GuodongSensor findByEUI(String devEUI){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("devEUI", devEUI);
		List<GuodongSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
