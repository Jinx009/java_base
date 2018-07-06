package database.basicFunctions.dao.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter;
import database.common.OrderFilter.OrderType;
import database.common.SearchFilter.Operators;
import database.models.log.LogSensorStatus;
import utils.StringUtil;

@Repository
public class LogSensorStatusDao extends BaseDao<LogSensorStatus>{

	public PageDataList<LogSensorStatus> findUse(Integer p,Integer areaId,String mac,Integer size, String dateStr){
		QueryParam queryParam = QueryParam.getInstance();
		if(0!=areaId){
			queryParam.addParam("areaId", areaId);
		}
		if(StringUtil.isNotBlank(mac)){
			SearchFilter filter = new SearchFilter("mac", Operators.LIKE, mac);
			SearchFilter filter2 = new SearchFilter("mac", Operators.LIKE, mac);
			queryParam.addOrFilter(filter,filter2);
		}
		if(StringUtil.isNotBlank(dateStr)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				queryParam.addParam("changeTime", Operators.GTE, sdf.parse(dateStr+" 00:00:00"));
				queryParam.addParam("changeTime", Operators.LTE, sdf.parse(dateStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		queryParam.addPage(p,size);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
