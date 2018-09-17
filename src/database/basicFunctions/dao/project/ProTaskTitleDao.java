package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProTaskTitle;
import utils.BaseConstant;

@Repository
public class ProTaskTitleDao extends BaseDao<ProTaskTitle>{

	public List<ProTaskTitle> findAllList() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status", 1);
		return findByCriteria(queryParam);
	}

	public PageDataList<ProTaskTitle> homeList(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status", 1);
		queryParam.addOrder(OrderType.DESC, "id");
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		return findPageList(queryParam);
	}

}
