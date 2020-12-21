package service.basicFunctions.business;

import java.util.List;

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
	
	/**
	 * appinfo分页列表
	 * @param params
	 * @return
	 */
	public Resp<?> list(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer p = jsonObject.getInteger(BaseConstant.PAGE_INDEX);
			if(p==null||p==0){
				p = 1;
			}
			PageDataList<BusinessAppInfo> list = businessAppInfoDao.getPage(p);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * 新建appinfo
	 * @param params
	 * @return
	 */
	public Resp<?> create(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String appId = jsonObject.getString(BaseConstant.APP_ID);
			String desc = jsonObject.getString(BaseConstant.DESCRIPTION);
			String appSecret = jsonObject.getString(BaseConstant.APP_SECRET);
			BusinessAppInfo businessAppInfo = businessAppInfoDao.findByAppId(appId);
			if(businessAppInfo!=null){
				resp.setMsg(BaseConstant.APP_ID_IS_EXITS);
				return resp;
			}else{
				businessAppInfoDao.save(appId,appSecret,desc);
				resp = new Resp<>(true);
				return resp;
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * appinfo详情
	 * @param params
	 * @return
	 */
	public Resp<?> detail(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String appId = jsonObject.getString(BaseConstant.APP_ID);
			BusinessAppInfo businessAppInfo = businessAppInfoDao.findByAppId(appId);
			resp = new Resp<>(businessAppInfo);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * appinfo编辑
	 * @param params
	 * @return
	 */
	public Resp<?> edit(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String appId = jsonObject.getString(BaseConstant.APP_ID);
			String desc = jsonObject.getString(BaseConstant.DESCRIPTION);
			String appSecret = jsonObject.getString(BaseConstant.APP_SECRET);
			businessAppInfoDao.update(appId,desc,appSecret);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * appinfo删除
	 * @param params
	 * @return
	 */
	public Resp<?> delete(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			String appId = jsonObject.getString(BaseConstant.APP_ID);
			businessAppInfoDao.delete(appId);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * 不分页appinfo列表
	 * @param params
	 * @return
	 */
	public Resp<?> all(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			List<BusinessAppInfo> list = businessAppInfoDao.all();
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
}
