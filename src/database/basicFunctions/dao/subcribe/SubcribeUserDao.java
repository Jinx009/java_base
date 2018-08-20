package database.basicFunctions.dao.subcribe;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.subcribe.SubcribeUser;

@Repository
public class SubcribeUserDao extends BaseDao<SubcribeUser>{

	public SubcribeUser login(String mobilePhone, String pwd) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		queryParam.addParam("pwd", pwd);
		return findByCriteriaForUnique(queryParam);
	}
	
	public SubcribeUser findByMobilePhone(String mobilePhone){
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("mobilePhone", mobilePhone);
		return findByCriteriaForUnique(queryParam);
	}

}
