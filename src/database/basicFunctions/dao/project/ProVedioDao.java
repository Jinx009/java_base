package database.basicFunctions.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.project.ProVedio;

@Repository
public class ProVedioDao extends BaseDao<ProVedio>{

	public List<ProVedio> findByLevel(Integer level) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("vedioLevel", level);
		return findByCriteria(param);
	}

	
	
}
