package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayAccessControlAreaDao;
import database.models.project.ProGatewayAccessControlArea;

@Service
public class ProGatewayAccessControlAreaService {

	@Autowired
	private ProGatewayAccessControlAreaDao proGatewayAccessControlAreaDao;
	
	public ProGatewayAccessControlArea random(){
		return proGatewayAccessControlAreaDao.random();
	}
	
}
