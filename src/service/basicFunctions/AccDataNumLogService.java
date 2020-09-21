package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccDataNumLogDao;

@Service
public class AccDataNumLogService {

	@Autowired
	private AccDataNumLogDao accDataNumLogDao;
	
	
	
}
