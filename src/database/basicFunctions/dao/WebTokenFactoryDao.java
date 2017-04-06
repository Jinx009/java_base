package database.basicFunctions.dao;

import java.util.List;

import database.common.BaseDao;
import database.models.WebTokenFactory;

public interface WebTokenFactoryDao extends BaseDao<WebTokenFactory>{

	public List<WebTokenFactory> getByHql(String hql);
	
}
