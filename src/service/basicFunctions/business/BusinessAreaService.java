package service.basicFunctions.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.business.BusinessAreaDao;
import database.models.business.BusinessArea;

@Service
public class BusinessAreaService {

	@Autowired
	private BusinessAreaDao businessAreaDao;
	
	public BusinessArea findById(Integer id){
		return businessAreaDao.find(id);
	}
	
}
