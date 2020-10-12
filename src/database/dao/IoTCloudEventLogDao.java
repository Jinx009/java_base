package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.IoTCloudEventLog;
import utils.StringUtil;

@Repository
public class IoTCloudEventLogDao extends BaseDao<IoTCloudEventLog>{

	public PageDataList<IoTCloudEventLog> findByPage(String mac, String fatherType, String type, int p) {
		QueryParam param = QueryParam.getInstance();
		if(StringUtil.isNotBlank(mac)) {
			param.addParam("mac", mac);
		}
		if(StringUtil.isNotBlank(fatherType)) {
			param.addParam("fatherType", fatherType);
		}
		if(StringUtil.isNotBlank(type)) {
			param.addParam("type", type);
		}
		param.addPage(p, 30);
		param.addOrder(OrderType.DESC, "id");
		return findPageList(param);
	}
	

}
