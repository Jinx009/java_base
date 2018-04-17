package database.basicFunctions.dao.project;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.project.ProGatewayAccessControlArea;

@Repository
public class ProGatewayAccessControlAreaDao extends BaseDao<ProGatewayAccessControlArea>{

	public ProGatewayAccessControlArea random(){
		String sql = "select * from pro_gateway_access_control_area where parent_id!=0 order by rand() LIMIT 1";
		return findForUniqueBySql(sql,new String[]{},new String[]{});
	}
	
}
