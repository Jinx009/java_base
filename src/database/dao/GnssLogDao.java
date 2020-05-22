package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.model.GnssLog;

@Repository
public class GnssLogDao extends BaseDao<GnssLog>{

	public PageDataList<GnssLog> findByPage(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,40);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	@SuppressWarnings("unchecked")
	public List<GnssLog> getByDate(String startDate, String endDate,String mac, Integer fixType, Integer fixStatus, Integer horMin, Integer horMax, String type) {
		String hql = " from GnssLog where  mac = '"+mac+"' and dateTime>='"+startDate+"' and dateTime <= '"+endDate+"'  and  dataType = 1 ";
		if(fixType!=-1){
			hql+= " and fixType= "+fixType;
		}
		if(fixStatus!=-1){
			hql+= " and fixStatus= "+fixStatus;
		}
		if(horMin!=-1){
			hql+= " and horAcc>= "+horMin;
		}
		if(horMax!=-1){
			hql+= " and horAcc<= "+horMax;
		}
		if(!"-1".equals(type)){
			hql+= " and type= '"+type+"'";
		}
		List<GnssLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

	public PageDataList<GnssLog> openLogData(int i, String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(i,50);
		queryParam.addParam("mac", mac);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

}