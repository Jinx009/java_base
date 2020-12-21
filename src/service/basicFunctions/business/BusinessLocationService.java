package service.basicFunctions.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAppInfoDao;
import database.basicFunctions.dao.business.BusinessLocationDao;
import database.common.PageDataList;
import database.models.business.BusinessAppInfo;
import database.models.business.BusinessLocation;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class BusinessLocationService extends BaseService {

	private static final Logger log = LoggerFactory.getLogger(BusinessLocationService.class);

	@Autowired
	private BusinessLocationDao businessLocationDao;
	@Autowired
	private BusinessAppInfoDao businessAppInfoDao;

	/**
	 * location 列表 page
	 * @param params
	 * @return
	 */
	public Resp<?> list(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			if (p == null || p == 0) {
				p = 1;
			}
			PageDataList<BusinessLocation> list = businessLocationDao.findAll(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}
	
	/**
	 * location 列表
	 * @param params
	 * @return
	 */
	public Resp<?> all(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			if (p == null || p == 0) {
				p = 1;
			}
			List<BusinessLocation> list = businessLocationDao.all();
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}
	
	/**
	 * 更新
	 * @param 删除
	 * @return
	 */
	public Resp<?> delete(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer id = jsonObject.getInteger(BaseConstant.ID);
			BusinessLocation businessLocation = businessLocationDao.find(id);
			businessLocation.setRecSt(0);
			businessLocationDao.update(businessLocation);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}
	
	/**
	 * 更新
	 * @param params
	 * @return
	 */
	public Resp<?> edit(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			BusinessLocation businessLocation = JSONObject.parseObject(params, BusinessLocation.class);
			if(0!=businessLocation.getAppInfoId()){
				BusinessAppInfo businessAppInfo = businessAppInfoDao.find(businessLocation.getAppInfoId());
				businessLocation.setAppInfoDesc(businessAppInfo.getDescription());
			}
			businessLocationDao.update(businessLocation);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}

	/**
	 * 新建
	 * @param params
	 * @return
	 */
	public Resp<?> create(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			BusinessLocation businessLocation = JSONObject.parseObject(params, BusinessLocation.class);
			if(0!=businessLocation.getAppInfoId()){
				BusinessAppInfo businessAppInfo = businessAppInfoDao.find(businessLocation.getAppInfoId());
				businessLocation.setAppInfoDesc(businessAppInfo.getDescription());
			}
			businessLocationDao.create(businessLocation);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}
	
	/**
	 * 详情
	 * @param params
	 * @return
	 */
	public Resp<?> detail(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}", params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer id = jsonObject.getInteger(BaseConstant.ID);
			BusinessLocation businessLocation = businessLocationDao.find(id);
			resp = new Resp<>(businessLocation);
			return resp;
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}
	
}
