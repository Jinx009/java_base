package service.basicFunctions;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.WebTokenFactoryDao;
import database.common.QueryParam;
import database.models.WebTokenFactory;

@Service("webTokenFactoryService")
public class WebTokenFactoryServiceImpl implements WebTokenFactoryService{

	@Autowired
	private WebTokenFactoryDao webTokenFactoryDao;
	
	public WebTokenFactory getByTypeAndId(String baseId,Integer type){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("baseId",baseId);
		queryParam.addParam("type",type);
		return webTokenFactoryDao.findByCriteriaForUnique(queryParam);
	}

	public void update(WebTokenFactory webTokenFactory) {
		webTokenFactoryDao.update(webTokenFactory);
	}

	public void save(WebTokenFactory webTokenFactory) {
		webTokenFactoryDao.save(webTokenFactory);
	}
}
