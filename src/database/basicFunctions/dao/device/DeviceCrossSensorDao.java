package database.basicFunctions.dao.device;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.common.SearchFilter.Operators;
import database.models.device.DeviceCrossSensor;
import utils.StringUtil;
import utils.model.BaseConstant;


@Repository
public class DeviceCrossSensorDao extends BaseDao<DeviceCrossSensor>{

	public PageDataList<DeviceCrossSensor> getPage(Integer p,Integer areaId,String mac,Integer locationId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		if(0!=areaId){
			queryParam.addParam("areaId", areaId);
		}
		if(0!=locationId){
			queryParam.addParam("location", locationId);
		}
		if(StringUtil.isNotBlank(mac)){
			queryParam.addAddFilter("mac", Operators.LIKE, mac);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findPageList(queryParam);
	}
	
}
