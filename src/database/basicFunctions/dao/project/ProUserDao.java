package database.basicFunctions.dao.project;

import java.util.Date;

import org.springframework.stereotype.Repository;

import common.helper.MD5Util;
import database.common.BaseDao;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.common.QueryParam;
import database.models.project.ProUser;
import utils.BaseConstant;

@Repository
public class ProUserDao extends BaseDao<ProUser>{

	public ProUser getByUserNameAndPwd(String userName,String pwd){
		QueryParam param = QueryParam.getInstance();
		param.addParam("userName", userName);
		param.addParam("pwd",MD5Util.md5(pwd));
		return findByCriteriaForUnique(param);
	}
	
	public ProUser findByUserName(String userName){
		QueryParam param = QueryParam.getInstance();
		param.addParam("userName", userName);
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

	public PageDataList<ProUser> list(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}
	
}
