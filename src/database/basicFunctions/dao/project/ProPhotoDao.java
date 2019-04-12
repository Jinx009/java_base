package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProPhoto;
import utils.BaseConstant;

@Repository
public class ProPhotoDao extends BaseDao<ProPhoto>{

	public PageDataList<ProPhoto> pageList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "dateStr");
		return findPageList(queryParam);
	}

	public ProPhoto findByDateStr(String dateStr, int type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("dateStr", dateStr);
		queryParam.addParam("type", type);
		return findByCriteriaForUnique(queryParam);
	}

}
