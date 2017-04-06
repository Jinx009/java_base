package service.basicFunctions;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.WebTokenFactoryDao;
import database.models.WebTokenFactory;

@Service("webTokenFactoryService")
public class WebTokenFactoryServiceImpl implements WebTokenFactoryService{

	@Autowired
	private WebTokenFactoryDao webTokenFactoryDao;
	
	public WebTokenFactory getByTypeAndId(String baseId,Integer type){
		String hql = " FROM WebTokenFactory WHERE baseId = '"+baseId+"' AND type="+type+" ";
		List<WebTokenFactory> list = webTokenFactoryDao.getByHql(hql);
		if(list!=null){
			return list.get(0);
		}
		return null;
	}

	public void update(WebTokenFactory webTokenFactory) {
		webTokenFactoryDao.update(webTokenFactory);
	}

	public void save(WebTokenFactory webTokenFactory) {
		webTokenFactoryDao.save(webTokenFactory);
	}
}
