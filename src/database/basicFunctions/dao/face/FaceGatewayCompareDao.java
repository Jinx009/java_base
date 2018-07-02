package database.basicFunctions.dao.face;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.face.FaceGatewayCompare;

@Repository
public class FaceGatewayCompareDao extends BaseDao<FaceGatewayCompare>{

	public PageDataList<FaceGatewayCompare> pageList(Integer p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, 15);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
