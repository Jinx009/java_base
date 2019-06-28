package database.basicFunctions.dao.project;

import java.util.Date;

import org.springframework.stereotype.Repository;

import common.helper.MD5Util;
import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProUser;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser getByUserNameAndPwd(String userName,String pwd){
		QueryParam param = QueryParam.getInstance();
		param.addParam("userName", userName);
		param.addParam("pwd",MD5Util.md5(pwd));
		return findByCriteriaForUnique(param);
	}
	
	public ProUser register(String userName,String pwd){
		ProUser proUser = new ProUser();
		proUser.setCreateTime(new Date());
		proUser.setPwd(MD5Util.md5(pwd));
		proUser.setUserLevel(0);
		proUser.setUserName(userName);
		return save(proUser);
	}
	
	public void setLevel(Integer id){
		ProUser proUser = find(id);
		proUser.setLevelTime(new Date());
		proUser.setUserLevel(1);
		update(proUser);
	}
	
}
