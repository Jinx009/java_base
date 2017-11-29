package service.basicFunctions.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAppInfoDao;
import database.common.PageDataList;
import database.models.business.BusinessAppInfo;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class BusinessAppInfoService extends BaseService{

	private static final Logger log = LoggerFactory.getLogger(BusinessAppInfoService.class);
	
	@Autowired
	private BusinessAppInfoDao businessAppInfoDao;
	
	public Resp<?> list(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			if(p==null||p==0){
				p = 1;
			}
			PageDataList<BusinessAppInfo> list = businessAppInfoDao.findAll(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
