package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccDataLogDao;

@Service
public class AccDataLogService {

	@Autowired
	private AccDataLogDao accDataLogDao;
	
	public void save(String data,Integer type) {
		
	}
	
}
