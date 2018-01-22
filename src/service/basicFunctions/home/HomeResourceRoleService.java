package service.basicFunctions.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.home.HomeResourceRoleDao;
import database.models.home.HomeResourceRole;

@Service
public class HomeResourceRoleService {

	@Autowired
	private HomeResourceRoleDao homeResourceRoleDao;

	public List<HomeResourceRole> findByRoleId(Integer roleId){
		return homeResourceRoleDao.findByRole(roleId);
	}

	public String save(Integer roleId, String[] s) {
		List<HomeResourceRole> list = findByRoleId(roleId);
		for(HomeResourceRole homeResourceRole : list){
			homeResourceRole = homeResourceRoleDao.find(homeResourceRole.getId());
			homeResourceRoleDao.delete(homeResourceRole.getId());
		}
		Map<Integer, Integer> map = new HashMap<>();
		for(int i = 0;i<s.length;i++){
			System.out.println("--"+s[i]);
			map.put(Integer.valueOf(s[i]), null);
		}
		Set<Integer> keys = map.keySet() ;
		Iterator<Integer> iter = keys.iterator() ;
		while(iter.hasNext()){
			Integer str = iter.next() ;
			HomeResourceRole homeResourceRole = new HomeResourceRole();
			homeResourceRole.setCreateTime(new Date());
			homeResourceRole.setResourceId(str);
			homeResourceRole.setRoleId(roleId);
			homeResourceRoleDao.save(homeResourceRole);
		}
		return null;
	}
	
}
