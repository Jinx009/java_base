package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProSwimSwitch;
import utils.BaseConstant;

@Repository
public class ProSwimSwitchDao extends BaseDao<ProSwimSwitch>{

	public PageDataList<ProSwimSwitch> pageList(int p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "dateStr");
		return findPageList(queryParam);
	}
	
	public ProSwimSwitch findByDateStr(String dateStr){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("dateStr", dateStr);
		return findByCriteriaForUnique(queryParam);
	}
	
}
