package service.basicFunctions.subcribe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.subcribe.SubcribeParkPlaceDao;
import database.models.subcribe.SubcribeParkPlace;

@Service
public class SubcribeParkPlaceService {

	@Autowired
	private SubcribeParkPlaceDao subcribeParkPlaceDao;
	
	public SubcribeParkPlace findById(Integer id){
		return subcribeParkPlaceDao.find(id);
	}
	
	public int getTotal(Integer id){
		return subcribeParkPlaceDao.getTotal(id);
	}
	
	public List<SubcribeParkPlace> list(){
		return subcribeParkPlaceDao.findAll();
	}
	
}
