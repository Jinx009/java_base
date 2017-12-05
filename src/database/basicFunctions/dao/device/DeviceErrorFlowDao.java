package database.basicFunctions.dao.device;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter.Operators;
import database.common.OrderFilter.OrderType;
import database.models.device.DeviceErrorFlow;
import utils.model.BaseConstant;

@Repository
public class DeviceErrorFlowDao extends BaseDao<DeviceErrorFlow>{

	public PageDataList<DeviceErrorFlow> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addAddFilter("areaId",Operators.NOTEQ, null);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return findPageList(queryParam);
	}
	
	@SuppressWarnings("unchecked")
	public List<DeviceErrorFlow> findUse(){
		String sql = " select * from tbl_error_flow where rec_st = 1 and create_time > :createTime ";
		Query query = em.createNativeQuery(sql, DeviceErrorFlow.class).setParameter("createTime", "2017-11-04 00:00:00");
		List<DeviceErrorFlow> list = query.getResultList();
		return list;
	}
	
}
