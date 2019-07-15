package database.basicFunctions.dao.qj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.models.qj.QjDeviceLog;

@Repository
public class QjDeviceLogDao extends BaseDao<QjDeviceLog>{

	@SuppressWarnings("unchecked")
	public QjDeviceLog getNearBySn(String sn) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String dateStr = sdf.format(date);
			String hql = " from QjDeviceLog where snValue='"+sn+"' and  createTime >='"+dateStr+" 00:00:00' and createTime <= '"+dateStr+" 23:59:59' order by id desc ";
			List<QjDeviceLog> list = em.createQuery(hql).getResultList();
			if(list!=null&&!list.isEmpty()){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<QjDeviceLog> nearList() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String dateStr = sdf.format(date);
			String hql = " from QjDeviceLog where  createTime >='"+dateStr+" 00:00:00' and createTime <= '"+dateStr+" 23:59:59' order by id desc ";
			List<QjDeviceLog> list = em.createQuery(hql).getResultList();
			if(list!=null&&!list.isEmpty()){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object nearList(String mac, String date) {
		try {
			String date1 = date.split(" - ")[0];
			String date2 = date.split(" - ")[1];
			String hql = " from QjDeviceLog where  createTime >='"+date1+"' and createTime <= '"+date2+"'  order by id desc ";
			if(StringUtil.isNotBlank(mac)) {
				hql = " from QjDeviceLog where  createTime >='"+date1+"'  and createTime <= '"+date2+"'  and snValue like '%"+mac+"%' order by id desc ";
			}
			List<QjDeviceLog> list = em.createQuery(hql).getResultList();
			if(list!=null&&!list.isEmpty()){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
