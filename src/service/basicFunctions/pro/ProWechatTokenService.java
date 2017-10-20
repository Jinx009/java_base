package service.basicFunctions.pro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProWechatTokenDao;
import database.common.QueryParam;
import database.models.pro.ProWechatToken;

@Service
public class ProWechatTokenService {

	@Autowired
	private ProWechatTokenDao proWechatTokenDao;
	
	public ProWechatToken getByTypeAndId(String baseId,Integer type){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("baseId",baseId);
		queryParam.addParam("type",type);
		return proWechatTokenDao.findByCriteriaForUnique(queryParam);
	}

	public void update(ProWechatToken proWechatToken) {
		proWechatTokenDao.update(proWechatToken);
	}

	public void save(ProWechatToken proWechatToken) {
		proWechatTokenDao.save(proWechatToken);
}
	
}
