package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.project.ProAccessControlOldLog;

@Repository
public class ProAccessControlOldLogDao extends BaseDao<ProAccessControlOldLog>{

	public ProAccessControlOldLog random(){
		String sql = "select * from pro_gateway_old_access_control order by rand() LIMIT 1";
		return findForUniqueBySql(sql,new String[]{},new String[]{});
	}
	
}
