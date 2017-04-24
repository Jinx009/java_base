package service.basicFunctions.home;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
import common.helper.StringUtil;
import database.basicFunctions.dao.home.HomeUserDao;
import database.basicFunctions.dao.home.HomeUserRoleDao;
import database.common.QueryParam;
import database.models.home.HomeUser;
import database.models.home.HomeUserRole;
import database.models.home.vo.HomeUserVo;
import utils.RespData;

@Service
public class HomeUserService {

	@Autowired
	private HomeUserDao homeUserDao;
	@Autowired
	private HomeUserRoleDao homeUserRoleDao;
	
	public HomeUser login(String userName,String pwd){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName",userName);
		queryParam.addParam("pwd",pwd);
		queryParam.addParam("status",1);
		return homeUserDao.findByCriteriaForUnique(queryParam);
	}

	public List<HomeUserVo> getHomeUser() throws ParseException {
		return homeUserDao.getHomeUser();
	}

	public boolean add(HomeUser homeUser, Integer roleId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userName",homeUser.getUserName());
		HomeUser homeUser2 = homeUserDao.findByCriteriaForUnique(queryParam);
		if(homeUser2==null){
			homeUser.setCreateTime(new Date());
			homeUser.setStatus(1);
			homeUser.setPwd(MD5Util.toMD5(homeUser.getPwd()));
			homeUser = homeUserDao.save(homeUser);
			HomeUserRole homeUserRole = new HomeUserRole();
			homeUserRole.setCreateTime(new Date());
			homeUserRole.setRoleId(roleId);
			homeUserRole.setUserId(homeUser.getId());
			homeUserRoleDao.save(homeUserRole);
			return true;
		}
		return false;
	}

	public String update(HomeUser homeUser, Integer roleId, Integer userId) {
		HomeUser homeUser2 = homeUserDao.find(userId);
		if(StringUtil.isNotBlank(homeUser.getPwd())){
			homeUser2.setPwd(homeUser.getPwd());
		}
		homeUser2.setRealName(homeUser.getRealName());
		homeUserDao.update(homeUser2);
		HomeUserRole homeUserRole = homeUserRoleDao.findByUserId(userId);
		if(homeUserRole!=null&&homeUserRole.getRoleId()!=roleId){
			homeUserRole.setRoleId(roleId);
			homeUserRoleDao.update(homeUserRole);
		}
		return RespData.OK_CODE;
	}

	public void changeStatus(Integer status, Integer id) {
		HomeUser homeUser = homeUserDao.find(id);
		if(null!=homeUser){
			homeUser.setStatus(status);
			homeUserDao.update(homeUser);
		}
	}

	public void update(HomeUser homeUser) {
		homeUserDao.update(homeUser);
	}
	
}
