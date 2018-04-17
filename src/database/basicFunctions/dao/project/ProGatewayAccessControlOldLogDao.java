package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.project.ProGatewayAccessControlOldLog;

@Repository
public class ProGatewayAccessControlOldLogDao extends BaseDao<ProGatewayAccessControlOldLog>{

	public ProGatewayAccessControlOldLog random(){
		String sql = "select * from pro_gateway_old_access_control order by rand() LIMIT 1";
		return findForUniqueBySql(sql,new String[]{},new String[]{});
	}
	
	
	
}
