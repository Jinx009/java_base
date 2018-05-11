package database.basicFunctions.dao.device;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.SearchFilter.Operators;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter;
import database.models.device.DeviceSensor;
import utils.StringUtil;
import utils.model.BaseConstant;

@Repository
public class DeviceSensorDao extends BaseDao<DeviceSensor>{

	public List<DeviceSensor> findUse(){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findByCriteria(queryParam);
	}
	
	public PageDataList<DeviceSensor> findUse(Integer p,Integer areaId,String mac){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		if(0!=areaId){
			queryParam.addParam("areaId", areaId);
		}
		if(StringUtil.isNotBlank(mac)){
			SearchFilter filter = new SearchFilter("mac", Operators.LIKE, mac);
			SearchFilter filter2 = new SearchFilter("mac", Operators.LIKE, mac);
			queryParam.addOrFilter(filter,filter2);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.ASC, "mac");
		return findPageList(queryParam);
	}

	public DeviceSensor findByMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}

	public DeviceSensor findByParentMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("parentMac", mac);
		List<DeviceSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public DeviceSensor findByRouterMac(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("routerMac", mac);
		List<DeviceSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<DeviceSensor> findAllUse(Integer areaId, String mac) {
		String sql = " select * from tbl_sensor t where t.rec_st = 1 ";
		if(StringUtil.isNotBlank(mac)){
			sql += " and t.mac like '%"+mac+"%' ";
		}
		if(0!=areaId){
			sql += " and t.area_id = "+areaId;
		}else{
			sql += " and t.area_id is null  ";
		}
		sql += " order by t.area_id ";
		System.out.println(sql);
		Query query = em.createNativeQuery(sql, DeviceSensor.class);
		List<DeviceSensor> list = query.getResultList();
		return list;
	}
	
}
