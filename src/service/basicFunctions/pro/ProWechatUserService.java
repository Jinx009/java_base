package service.basicFunctions.pro;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProWechatConnDao;
import database.basicFunctions.dao.pro.ProWechatUserDao;
import database.common.OrderFilter.OrderType;
import database.common.QueryParam;
import database.models.pro.ProWechatConn;
import database.models.pro.ProWechatUser;
import utils.BufferUtils;

@Service
public class ProWechatUserService {

	@Autowired
	private ProWechatUserDao proWechatUserDao;
	@Autowired
	private ProWechatConnDao proWechatConnDao;

	public List<ProWechatUser> findRegisterUser() {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",1);
		queryParam.addOrder(OrderType.DESC, "createTime");
		return proWechatUserDao.findByCriteria(queryParam);
	}

	public List<ProWechatUser> findUserfulUser(Integer userId) {
		ProWechatUser proWechatUser = proWechatUserDao.find(userId);
		String ids = "";
		String sex = "男";
		if("男".equals(proWechatUser.getSex())){
			sex = "女";
		}
		String hql2 = BufferUtils.add(" FROM ProWechatConn WHERE first = ",String.valueOf(userId)," OR (status!=0 ANDlikeUserId = ",String.valueOf(userId),")");
		List<ProWechatConn> lConns = proWechatConnDao.findByHql(hql2);
		if(lConns!=null&&!lConns.isEmpty()){
			for(ProWechatConn pConn:lConns){
				if(pConn.getFirst()==userId){
					ids += pConn.getLikeUserId()+",";
				}else{
					ids += pConn.getUserId()+",";
				}
			}
			ids = ids.substring(0, ids.length() - 1);
			String hql =  BufferUtils.add(" FROM ProWechatUser WHERE id!= ",String.valueOf(userId)," AND sex = '",sex,"' AND id not in(",ids,")");
			List<ProWechatUser> list = proWechatUserDao.findByHql(hql);
			return list;
		}else{
			String hql =  BufferUtils.add(" FROM ProWechatUser WHERE id!= ",String.valueOf(userId)," AND sex = '",sex,"'");
			List<ProWechatUser> list = proWechatUserDao.findByHql(hql);
			return list;
		}
	}
	
	public ProWechatUser find(Integer id){
		ProWechatUser proWechatUser = proWechatUserDao.find(id);
		if((proWechatUser.getPic1()==null||"".equals(proWechatUser.getPic1()))&&(proWechatUser.getPic2()!=null||!"".equals(proWechatUser.getPic2()))){
			proWechatUser.setPic1(proWechatUser.getPic2());
			return proWechatUser;
		}
		if((proWechatUser.getPic1()==null||"".equals(proWechatUser.getPic1()))&&(proWechatUser.getPic3()!=null||!"".equals(proWechatUser.getPic3()))){
			proWechatUser.setPic1(proWechatUser.getPic2());
			return proWechatUser;
		}
		return proWechatUser;
	}

	public void update(ProWechatUser proWechatUser) {
		proWechatUserDao.update(proWechatUser);
	}

	public ProWechatUser findByOpenId(String openid) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("openid", openid);
		List<ProWechatUser> list = proWechatUserDao.findByCriteria(queryParam);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public ProWechatUser saveNew(String openid, String qKey) {
		ProWechatUser user = new ProWechatUser();
		user.setCreateTime(new Date());
		user.setOpenid(openid);
		user.setStatus(0);
		user.setLoginTime(new Date());
		user.setLoginTimes(1);
		user.setQrcode(qKey);
		return proWechatUserDao.save(user);
	}
	
}
