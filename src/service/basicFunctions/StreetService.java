package service.basicFunctions;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.StreetDao;
import database.common.PageDataList;
import database.models.Street;
import utils.StringUtil;

@Service
public class StreetService {

	@Autowired
	private StreetDao streetDao;
	
    
    public void save(String streetNumber,String address,Integer parkTotal,String name){
    	Street street = new Street();
    	street.setStreetNumber(streetNumber);
    	street.setAddress(address);
    	street.setParkTotal(parkTotal);
    	street.setName(name);
    	street.setCreateTime(new Date());
    	streetDao.save(street);
    }
    
    public void update(Integer id,String streetNumber,String address,Integer parkTotal,String name){
    	Street street = streetDao.find(id);
    	if(StringUtil.isNotBlank(streetNumber)){
    		street.setStreetNumber(streetNumber);
    	}
    	if(StringUtil.isNotBlank(address)){
    		street.setAddress(address);
    	}
    	if(parkTotal!=null&&parkTotal!=0){
    		street.setParkTotal(parkTotal);
    	}
    	if(StringUtil.isNotBlank(name)){
    		street.setName(name);
    	}
    	streetDao.update(street);
    }
    
    public PageDataList<Street> findByPage(int p){
    	return streetDao.findByPage(p);
    }
	
    
    public void delete(Integer id){
    	streetDao.delete(id);
    }

	public List<Street> findAll() {
		return streetDao.findAll();
	}

	public Street findById(Integer id) {
		return streetDao.find(id);
	}
    
}
