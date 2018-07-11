package database.basicFunctions.dao.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.SearchFilter.Operators;
import database.common.OrderFilter.OrderType;
import database.models.project.ProTask;
import utils.BaseConstant;

@Repository
public class ProTaskDao extends BaseDao<ProTask>{

	public PageDataList<ProTask> homeList(Integer p,Integer status,String driverName,String fromDate,String endDate) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(driverName)){
			queryParam.addParam("driverName", driverName);
		}
		if(status!=2){
			queryParam.addParam("status", status);
		}
		if(StringUtil.isNotBlank(fromDate)&&StringUtil.isNotBlank(endDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				queryParam.addParam("pickTime", Operators.GTE, sdf.parse(fromDate+" 00:00:00"));
				queryParam.addParam("pickTime", Operators.LTE, sdf.parse(endDate+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
	public List<ProTask> excelList(Integer status,String driverName,String fromDate,String endDate) {
		QueryParam queryParam = QueryParam.getInstance();
		if(StringUtil.isNotBlank(driverName)){
			queryParam.addParam("driverName", driverName);
		}
		if(status!=2){
			queryParam.addParam("status", status);
		}
		if(StringUtil.isNotBlank(fromDate)&&StringUtil.isNotBlank(endDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				queryParam.addParam("pickTime", Operators.GTE, sdf.parse(fromDate+" 00:00:00"));
				queryParam.addParam("pickTime", Operators.LTE, sdf.parse(endDate+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	public List<ProTask> findWait(String driverMobile) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("driverMobile", driverMobile);
		queryParam.addParam("status", 0);
		queryParam.addOrder(OrderType.ASC, "pickTime");
		return findByCriteria(queryParam);
	}
	
}