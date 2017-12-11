package service.basicFunctions.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.basicFunctions.dao.business.BusinessLocationDao;
import database.common.PageDataList;
import database.models.business.BusinessArea;
import database.models.business.BusinessLocation;
import database.models.business.vo.BusinessAreaVo;
import service.basicFunctions.BaseService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class BusinessAreaService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(BusinessAreaService.class);

	@Autowired
	private BusinessAreaDao businessAreaDao;
	@Autowired
	private BusinessLocationDao businessLocationDao;
	
	
	/**
	 * Area 列表 带分页
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
			PageDataList<BusinessArea> list = businessAreaDao.findAll(p);
			PageDataList<BusinessAreaVo> vos = new PageDataList<BusinessAreaVo>();
			List<BusinessAreaVo> vo = new ArrayList<BusinessAreaVo>();
			vos.setPage(list.getPage());
			if(list!=null&&list.getList()!=null&&!list.getList().isEmpty()){
				for(BusinessArea businessArea:list.getList()){
					BusinessAreaVo businessAreaVo = BusinessAreaVo.instance(businessArea);
					BusinessLocation businessLocation = businessLocationDao.find(businessArea.getLocationId());
					businessAreaVo.setLocationName(businessLocation.getName());
					vo.add(businessAreaVo);
				}
			}
			vos.setList(vo);
			resp = new Resp<>(vos);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Area列表
	 * @param params
	 * @return
	 */
	public Resp<?> all(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer id = jsonObject.getInteger(BaseConstant.LOCATION_ID);
			if(id==null||id==0){
				return resp;
			}
			List<BusinessArea> list = businessAreaDao.all(id);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Area 详情
	 * @param params
	 * @return
	 */
	public Resp<?> detail(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer id = jsonObject.getInteger(BaseConstant.ID);
			BusinessArea businessArea = businessAreaDao.find(id);
			resp = new Resp<>(businessArea);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Area 详情
	 * @param params
	 * @return
	 */
	public Resp<?> edit(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			BusinessArea businessArea = JSONObject.parseObject(params,BusinessArea.class);
			businessAreaDao.update(businessArea);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Area 详情
	 * @param params
	 * @return
	 */
	public Resp<?> create(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			BusinessArea businessArea = JSONObject.parseObject(params,BusinessArea.class);
			businessAreaDao.create(businessArea);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	/**
	 * Area 删除
	 * @param params
	 * @return
	 */
	public Resp<?> delete(String params){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("params:{}",params);
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer id = jsonObject.getInteger(BaseConstant.ID);
			BusinessArea businessArea = businessAreaDao.find(id);
			businessArea.setRecSt(0);
			businessAreaDao.update(businessArea);
			resp = new Resp<>(true);
			return resp;
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
	
}
