package database.basicFunctions.dao.face;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.face.FaceGatewayUser;

@Repository
public class FaceGatewayUserDao extends BaseDao<FaceGatewayUser>{

	public PageDataList<FaceGatewayUser> pageList(Integer p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, 25);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
	public FaceGatewayUser getByMobilePhone(String mobilePhone){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}
	
}
