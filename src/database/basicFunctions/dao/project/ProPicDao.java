package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProPic;
import utils.BaseConstant;

@Repository
public class ProPicDao extends BaseDao<ProPic>{

	public List<ProPic> frontList() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addOrder(OrderType.DESC,"createTime");
		queryParam.addOrder(OrderType.DESC,"star");
		return findByCriteria(queryParam);
	}

	public PageDataList<ProPic> homeList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
