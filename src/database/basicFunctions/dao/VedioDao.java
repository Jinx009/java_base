package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.Vedio;
import utils.StringUtil;
import utils.model.BaseConstant;

@Repository
public class VedioDao extends BaseDao<Vedio>{

	public PageDataList<Vedio> findByPage(int p, Integer streetId, String parkNumber) {
		QueryParam queryParam = QueryParam.getInstance();
		if(streetId!=null&&streetId!=0){
			queryParam.addParam("streetId", streetId);
		}
		if(StringUtil.isNotBlank(parkNumber)){
			queryParam.addParam("parkNumber", parkNumber);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}
