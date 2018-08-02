package database.basicFunctions.dao.qj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.common.SearchFilter.Operators;
import database.models.qj.QjDeviceLog;

@Repository
public class QjDeviceLogDao extends BaseDao<QjDeviceLog>{

	public QjDeviceLog getNearBySn(String sn) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("snValue", sn);
		queryParam.addOrder(OrderType.DESC,"id");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String dateStr = sdf.format(date);
			queryParam.addParam("createTime", Operators.GTE, sdf.parse(dateStr+" 00:00:00"));
			queryParam.addParam("createTime", Operators.LTE, sdf.parse(dateStr+" 23:59:59"));
			List<QjDeviceLog> list = findByCriteria(queryParam);
			if(list!=null&&!list.isEmpty()){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
