package service.basicFunctions.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.home.HomeResourceDao;
import database.models.home.HomeResource;

@Service
public class HomeResourceService {

	@Autowired
	private HomeResourceDao homeResourceDao;
	
	public List<HomeResource> getMenu(Integer homeUserId){
		return homeResourceDao.getMenu(homeUserId);
	}

	public List<HomeResource> getPageResource() {
		return homeResourceDao.getPageResource();
	}

	public List<HomeResource> getDataResource() {
		return homeResourceDao.getDataResource();
	}
	
}
