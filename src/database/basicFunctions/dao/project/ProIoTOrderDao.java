package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
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

}
