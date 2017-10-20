package service.basicFunctions.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProAdminUserDao;
import database.common.QueryParam;
import database.models.pro.ProAdminUser;

@Service
public class ProAdminUserService {

	@Autowired
	private ProAdminUserDao proAdminUserDao;
	
	public ProAdminUser login(String userName,String password){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName", userName);
		queryParam.addParam("pwd",password);
		List<ProAdminUser> list = proAdminUserDao.findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
