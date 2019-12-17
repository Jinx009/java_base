package database.basicFunctions.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.ParkingRecord;
import utils.StringUtil;
import utils.model.BaseConstant;

@Repository
public class ParkingRecordDao extends BaseDao<ParkingRecord> {

	public PageDataList<ParkingRecord> findByPage(int p, String parkNumber) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(parkNumber)){
			queryParam.addParam("parkNumber", parkNumber);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<ParkingRecord> findByDate() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.ASC, "vedioTime");
		return findByCriteria(queryParam);
	}
	


}
