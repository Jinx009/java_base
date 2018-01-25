package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProIoTOrder;

@Repository
public class ProIoTOrderDao extends BaseDao<ProIoTOrder>{

	public ProIoTOrder findByMacNear(String mac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mac",mac);
		queryParam.addOrder(OrderType.DESC,"id");
		List<ProIoTOrder> list = findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public PageDataList<ProIoTOrder> findUse(Integer p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, 25);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<ProIoTOrder> findByStatus(String status) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",status);
		queryParam.addOrder(OrderType.DESC,"id");
		return findByCriteria(queryParam);
	}

}
