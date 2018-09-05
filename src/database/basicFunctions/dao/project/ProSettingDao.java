package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProSetting;

@Repository
public class ProSettingDao extends BaseDao<ProSetting>{

	public ProSetting getByName(String name) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("name", name);
		return findByCriteriaForUnique(queryParam);
	}
	
}
