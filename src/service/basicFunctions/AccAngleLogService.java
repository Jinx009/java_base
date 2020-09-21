package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccAngleLogDao;

@Service
public class AccAngleLogService {

	@Autowired
	private AccAngleLogDao accAngleLogDao;
	
}
