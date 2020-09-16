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
	
	public List<DeviceSensor> findByParentMacList(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("parentMac", mac);
		queryParam.addOrder(OrderType.ASC, "desc");
		List<DeviceSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list;
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
		Query query = em.createNativeQuery(sql, DeviceSensor.class);
		List<DeviceSensor> list = query.getResultList();
		return list;
	}

	public List<DeviceSensor> getSensorsByArea(int i) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("areaId", i);
		List<DeviceSensor> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<DeviceSensor> install() {
		String sql = " select * from tbl_sensor t where t.area_id!=64 and mac like '%0001191107%' order by mac ";
		Query query = em.createNativeQuery(sql, DeviceSensor.class);
		List<DeviceSensor> list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<String> findParentMacByLike(String address) {
		String sql = "select distinct(parent_mac) from tbl_sensor  where parent_mac like '%"+address+"%'";
		List<String> list = em.createNativeQuery(sql,String.class).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<DeviceSensor> findByMacLike(String mac) {
		String sql = " select * from tbl_sensor where mac like '%"+mac+"%' order by create_time desc ";
		Query query = em.createNativeQuery(sql, DeviceSensor.class);
		List<DeviceSensor> list = query.getResultList();
		return list;
	}
	
}
