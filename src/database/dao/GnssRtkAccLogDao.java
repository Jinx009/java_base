package database.dao;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.model.GnssRtkAccLog;
import utils.BaseConstant;

@Repository
public class GnssRtkAccLogDao extends BaseDao<GnssRtkAccLog>{

	public PageDataList<GnssRtkAccLog> findByPageAndMac(int p, String mac) {
		QueryParam param = QueryParam.getInstance();
		param.addPage(p,BaseConstant.PAGE_SIZE);
		if(StringUtil.isNotBlank(mac)) {
			param.addParam("mac", mac);
		}
		return findPageList(param);
	}


}
