package service.basicFunctions.project;


import database.basicFunctions.dao.project.ProGatewayAreaDao;
import database.models.project.ProGatewayArea;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProGatewayAreaService {

	@Autowired
	private ProGatewayAreaDao proGatewayAreaDao;
	
	public ProGatewayArea getByAreaId(Integer areaId){
		return proGatewayAreaDao.getByAreaId(areaId);
	}

	public List<ProGatewayArea> getByLocationId(Integer locationId) {
		return proGatewayAreaDao.findByLocationId(locationId);
	}
	
}
