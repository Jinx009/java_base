package database.basicFunctions.dao.subcribe;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.subcribe.SubcribeParkPlace;

@Repository
public class SubcribeParkPlaceDao extends BaseDao<SubcribeParkPlace>{

	public int getTotal(int id){
		SubcribeParkPlace subcribeParkPlace = find(id);
		return subcribeParkPlace.getNumber();
	}
	
}
