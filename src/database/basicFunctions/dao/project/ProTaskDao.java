package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter;
import database.common.OrderFilter.OrderType;
import database.models.project.ProTask;
import utils.BaseConstant;

@Repository
public class ProTaskDao extends BaseDao<ProTask> {

	public PageDataList<ProTask> homeList(Integer p, Integer status, String driverName, Integer taskTitleId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("showStatus", 1);
		if (StringUtil.isNotBlank(driverName)) {
			queryParam.addParam("driverName", driverName);
		}
		if (status != 2) {
			queryParam.addParam("status", status);
		}
		if (0 != taskTitleId) {
			queryParam.addParam("taskTitleId", taskTitleId);
		}
		if (status == 1) {
			queryParam.addOrder(OrderType.DESC, "pickedTime");
			queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		} else {
			queryParam.addOrder(OrderType.DESC, "id");
			queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		}
		return findPageList(queryParam);
	}

	public List<ProTask> excelList(Integer titleId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("taskTitleId", titleId);
		queryParam.addOrder(OrderType.ASC, "id");
		return findByCriteria(queryParam);
	}

	@SuppressWarnings("unchecked")
	public List<ProTask> findWait(String driverMobile) {
		String hql = " from ProTask where status = 0 and (driverMobile = 0 or driverMobile = '" + driverMobile
				+ "') and showStatus = 1 order by pickTime asc,dateStr asc ";
		List<ProTask> list = em.createQuery(hql).getResultList();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public PageDataList<ProTask> frontWaitList(Integer p, String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		SearchFilter searchFilter = new SearchFilter("driverMobile", "0");
		SearchFilter searchFilter2 = new SearchFilter("driverMobile", mobilePhone);
		queryParam.addOrFilter(searchFilter,searchFilter2);
		queryParam.addParam("status", 0);
		queryParam.addParam("showStatus", 1);
		queryParam.addOrder(OrderType.DESC, "id");
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		return findPageList(queryParam);
	}
	
	public PageDataList<ProTask> frontDoneList(Integer p, String mobilePhone) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("driverMobile", mobilePhone);
		queryParam.addParam("status", 1);
		queryParam.addOrder(OrderType.DESC, "id");
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		return findPageList(queryParam);
	}

	public List<ProTask> findByTitleId(Integer titleId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("taskTitleId", titleId);
		return findByCriteria(queryParam);
	}

}
