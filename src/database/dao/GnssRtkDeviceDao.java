package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.model.GnssRtkDevice;

@Repository
public class GnssRtkDeviceDao extends BaseDao<GnssRtkDevice>{

	public GnssRtkDevice findByRoverTag(String roverTag) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("rovertag", roverTag);
		return findByCriteriaForUnique(param);
	}
	
	public GnssRtkDevice findByNewTime() {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC,"updatetime");
		List<GnssRtkDevice> list = findByCriteria(param);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public GnssRtkDevice findByNew2Time() {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC,"updatetime2");
		List<GnssRtkDevice> list = findByCriteria(param);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public GnssRtkDevice findByMac(String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("mac", mac);
		return findByCriteriaForUnique(param);
	}

	public PageDataList<GnssRtkDevice> page(int p) {
		QueryParam param = QueryParam.getInstance();
		param.addOrder(OrderType.DESC, "id");
		param.addPage(p, 10);
		return findPageList(param);
	}


}
