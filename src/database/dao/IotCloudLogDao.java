package database.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.IotCloudLog;
import utils.BaseConstant;
import utils.StringUtil;

@Repository
public class IotCloudLogDao extends BaseDao<IotCloudLog>{

	public PageDataList<IotCloudLog> findAll(Integer p, Integer type, String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",type);
		if(StringUtil.isNotBlank(mac)){
			queryParam.addParam("mac",mac);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public Object findAll(Integer p, Integer type, String mac, String fromSite) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("type",type);
		queryParam.addParam("fromSite",fromSite);
		if(StringUtil.isNotBlank(mac)){
			queryParam.addParam("mac",mac);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
	@SuppressWarnings("unchecked")
	public List<IotCloudLog> findByMacLike(String mac) {
		String sql = " select *  from pro_log  where mac like  '%"+mac.toUpperCase()+"%'  order by id desc ";
		System.out.println("sql:"+sql);
		Query query = em.createNativeQuery(sql, IotCloudLog.class);
		List<IotCloudLog> list = query.getResultList();
		return list;
	}
	
}
