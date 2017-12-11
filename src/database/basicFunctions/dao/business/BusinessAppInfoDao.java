package database.basicFunctions.dao.business;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.business.BusinessAppInfo;
import utils.StringUtil;
import utils.model.BaseConstant;

@Repository
public class BusinessAppInfoDao extends BaseDao<BusinessAppInfo>{

	public PageDataList<BusinessAppInfo> getPage(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
	public List<BusinessAppInfo> all() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	public BusinessAppInfo findByAppId(String appId) {
		if(StringUtil.isNotBlank(appId)){
			QueryParam queryParam = QueryParam.getInstance();
			queryParam.addParam("appId",appId);
			return findByCriteriaForUnique(queryParam);
		}
		return null;
	}

	public void save(String appId, String appSecret, String desc) {
		BusinessAppInfo businessAppInfo = new BusinessAppInfo();
		businessAppInfo.setAppId(appId);
		businessAppInfo.setAppSecret(appSecret);
		businessAppInfo.setCreateTime(new Date());
		businessAppInfo.setDescription(desc);
		businessAppInfo.setNoticeUrl("");
		businessAppInfo.setPath("");
		businessAppInfo.setRecSt(1);
		save(businessAppInfo);
	}
	
	public void delete(String appId) {
		BusinessAppInfo businessAppInfo = findByAppId(appId);
		businessAppInfo.setRecSt(0);
		update(businessAppInfo);
	}

	public void update(String appId, String desc, String appSecret) {
		BusinessAppInfo businessAppInfo = findByAppId(appId);
		businessAppInfo.setAppSecret(appSecret);
		businessAppInfo.setDescription(desc);
		update(businessAppInfo);
	}
	
}
