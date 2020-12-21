package database.basicFunctions.dao.device;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter;
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
			SearchFilter filter = new SearchFilter("mac", Operators.LIKE, mac);
			SearchFilter filter2 = new SearchFilter("mac", Operators.LIKE, mac);
			queryParam.addOrFilter(filter,filter2);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findPageList(queryParam);
	}

	public DeviceCrossSensor findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac",mac);
		return findByCriteriaForUnique(queryParam);
	}
	
	public List<DeviceCrossSensor> findAllUse() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt",1);
		queryParam.addOrder(OrderType.ASC,"areaId");
		return findByCriteria(queryParam);
	}
	
}
