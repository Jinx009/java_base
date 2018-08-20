package database.basicFunctions.dao.subcribe;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.subcribe.SubcribeOrder;

@Repository
public class SubcribeOrderDao extends BaseDao<SubcribeOrder>{

	@SuppressWarnings("unchecked")
	public int getUse(Integer parkId,Integer startHour,Integer endHour,String dateStr){
		String hql = " from SubcribeOrder where parkId = "+parkId+
				" and ((startHour >= "+startHour+" and endHour <= "+endHour+")"+
				" or (startHour <= "+startHour+" and endHour > "+startHour+" ) ) and dateStr = '"+dateStr+"'  ";
		List<SubcribeOrder> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list.size();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public int getUseByPlateNumber(Integer parkId,Integer startHour,Integer endHour,String dateStr,String plateNumber){
		String hql = " from SubcribeOrder where parkId = "+parkId+
				" and ((startHour >= "+startHour+" and endHour <= "+endHour+")"+
				" or (startHour <= "+startHour+" and endHour > "+startHour+" )  ) and dateStr = '"+dateStr+"' and plateNumber = '"+plateNumber+"' ";
		List<SubcribeOrder> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list.size();
		}
		return 0;
	}

	public List<SubcribeOrder> findByMobilePhone(String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addOrder(OrderType.DESC,"id");
		return findByCriteria(queryParam);
	}
	
}
