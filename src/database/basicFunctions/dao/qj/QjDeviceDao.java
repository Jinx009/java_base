package database.basicFunctions.dao.qj;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.qj.QjDevice;

@Repository
public class QjDeviceDao extends BaseDao<QjDevice>{

	public List<QjDevice> findAllList() {
		return findAll();
	}
	
	
	public QjDevice findBySn(String sn){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("snValue", sn);
		return findByCriteriaForUnique(queryParam);
	}
	
}
