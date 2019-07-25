package database.basicFunctions.dao.vedio;

import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.vedio.VedioArea;

@Repository
public class VedioAreaDao extends BaseDao<VedioArea>{

	public VedioArea save(VedioArea vedioArea){
		return save(vedioArea);
	}
	
	public List<VedioArea> findList(){
		return findAll();
	}
	
}
