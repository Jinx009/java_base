package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProClassOrder;
import utils.BaseConstant;

@Repository
public class ProClassOrderDao extends BaseDao<ProClassOrder>{

	
	public List<ProClassOrder> findByMbilePhone(String mobilePhone){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	public PageDataList<ProClassOrder> homeList(Integer p, String classDate) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(classDate)){
			queryParam.addParam("classDate",classDate);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public ProClassOrder findByUserIdAndClassId(String userId, Integer classId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userId",userId);
		queryParam.addParam("classId",classId);
		return findByCriteriaForUnique(queryParam);
	}

	public List<ProClassOrder> list(Integer userId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userId",userId);
		queryParam.addOrder(OrderType.DESC,"classDate");
		return findByCriteria(queryParam);
	}
	
}
