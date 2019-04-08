package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.Camera;
import utils.model.BaseConstant;

@Repository
public class CameraDao extends BaseDao<Camera>{

	public PageDataList<Camera> findByPage(Integer streetId, int pageNum) {
		QueryParam queryParam = QueryParam.getInstance();
		if(streetId!=null&&streetId!=0){
			queryParam.addParam("streetId", streetId);
		}
		queryParam.addPage(pageNum, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}
