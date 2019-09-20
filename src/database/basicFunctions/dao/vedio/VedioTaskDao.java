package database.basicFunctions.dao.vedio;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.QueryParam;
import database.models.vedio.VedioTask;

@Repository
public class VedioTaskDao extends BaseDao<VedioTask>{

	public List<VedioTask> findByStatus(Integer status) {
		QueryParam param = QueryParam.getInstance();
		param.addParam("status", status);
		return findByCriteria(param);
	}

	
	
}
