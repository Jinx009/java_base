package database.basicFunctions.dao;

import java.util.List;

import database.common.BaseDao;
import database.models.User;

public interface UserDao extends BaseDao<User>{

	public List<User> getByHql(String hql);
	
}
