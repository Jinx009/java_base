package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.project.ProSwitch;
import utils.BaseConstant;

@Repository
public class ProSwitchDao extends BaseDao<ProSwitch>{

	public PageDataList<ProSwitch> pageList(int p){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "dateStr");
		return findPageList(queryParam);
	}
	
   public ProSwitch findByDateStr(String dateStr,Integer type){
	   QueryParam queryParam = QueryParam.getInstance();
	   queryParam.addParam("dateStr", dateStr);
	   queryParam.addParam("type", type);
	   return findByCriteriaForUnique(queryParam);
   }
	
}
