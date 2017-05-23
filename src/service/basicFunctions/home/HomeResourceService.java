package service.basicFunctions.home;

import java.util.Date;
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

	public void saveData(String name, String description, String uri) {
		HomeResource homeResource = new HomeResource();
		homeResource.setCreateTime(new Date());
		homeResource.setDescription(description);
		homeResource.setName(name);
		homeResource.setParentId(0);
		homeResource.setType(1);
		homeResource.setStatus(1);
		homeResource.setUri(uri);
		homeResourceDao.save(homeResource);
	}

	public void updateStatus(Integer status, Integer id) {
		HomeResource homeResource = homeResourceDao.find(id);
		homeResource.setStatus(status);
		homeResourceDao.update(homeResource);
	}
	
}
