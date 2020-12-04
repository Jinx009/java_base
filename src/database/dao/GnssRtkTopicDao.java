package database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.model.GnssRtkTopic;

@Repository
public class GnssRtkTopicDao extends BaseDao<GnssRtkTopic>{

	public GnssRtkTopic findByMacAndTopic(String mac, String topic) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("topic", topic);
		queryParam.addParam("mac", mac);
		return findByCriteriaForUnique(queryParam);
	}

	public PageDataList<GnssRtkTopic> list(String mac, int p) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(mac)) {
			queryParam.addParam("mac", mac);
		}
		queryParam.addPage(p, 10);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<GnssRtkTopic> list(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(mac)) {
			queryParam.addParam("mac", mac);
		}
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	
	
}
