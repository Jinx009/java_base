package database.basicFunctions.dao.home;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.home.HomeUser;
import database.models.home.vo.HomeUserVo;

@Repository
public class HomeUserDao extends BaseDao<HomeUser>{

	@SuppressWarnings("unchecked")
	public List<HomeUserVo> getHomeUser() {
		String sql = "SELECT a.USER_NAME,a.ID,b.NAME,a.CREATE_TIME,b.LEVEL,a.STATUS FROM HOME_USER a,HOME_ROLE b,HOME_USER_ROLE c WHERE a.ID = c.USER_ID AND c.ROLE_ID = b.ID";
		Query query = em.createNativeQuery(sql);
		List<Object> rows = query.getResultList();
		List<HomeUserVo> list = new ArrayList<HomeUserVo>();
		if(rows!=null&&rows.size()>0){
			for(Object row:rows){
				Object[] cells = (Object[]) row; 
				HomeUserVo homeUserVo = new HomeUserVo();
				homeUserVo.setUserName(cells[0].toString());
				homeUserVo.setId(Integer.valueOf(cells[1].toString()));
				homeUserVo.setRoleName(cells[2].toString());
				homeUserVo.setCreateTime(cells[3].toString());
				homeUserVo.setLevel(Integer.valueOf(cells[4].toString()));
				homeUserVo.setStatus(Integer.valueOf(cells[5].toString()));
				list.add(homeUserVo);
			}
		}
		return list;
	}

}
