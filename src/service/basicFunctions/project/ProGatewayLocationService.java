package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayLocationDao;
import database.models.project.ProGatewayLocation;

@Service
public class ProGatewayLocationService {

	@Autowired
	private ProGatewayLocationDao proGatewayLocationDao;
	
	public ProGatewayLocation getByLocationId(Integer locationId){
		return proGatewayLocationDao.getByLocationId(locationId);
	}

	public List<ProGatewayLocation> getByAppId(String appId) {
		return proGatewayLocationDao.getByAppId(appId);
	}
	
	public ProGatewayLocation getSingleByAppId(String appId) {
		List<ProGatewayLocation> list = proGatewayLocationDao.getByAppId(appId);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}