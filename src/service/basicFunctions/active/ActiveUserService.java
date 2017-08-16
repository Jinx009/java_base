package service.basicFunctions.active;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.active.ActiveUserDao;
import database.models.active.ActiveUser;

@Service
public class ActiveUserService {

	
	@Autowired
	private ActiveUserDao activeUserDao;
	
	public List<ActiveUser> getByActiveId(Integer activeId){
		return activeUserDao.getByActiveId(activeId);
	}
	
	public void save(ActiveUser activeUser){
		activeUserDao.save(activeUser);
	}

	public ActiveUser getByMobilePhone(String mobilePhone) {
		return activeUserDao.getByMobilePhone(mobilePhone);
	}
	
}
