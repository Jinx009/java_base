package database.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.model.GnssRtkLog;

@Repository
public class GnssRtkLogDao extends BaseDao<GnssRtkLog>{

	public PageDataList<GnssRtkLog> pages(Integer p, String mac, int type, int status) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addPage(p,10);
		if(StringUtil.isNotBlank(mac)) {
			queryParam.addParam("rovertag", mac);
		}
		if(-1!=type) {
			queryParam.addParam("type", type);
		}
		if(-1!=status) {
			queryParam.addParam("status", status);
		}
		queryParam.addOrder(OrderType.DESC, "updatetime");
		return findPageList(queryParam);
	}

	@SuppressWarnings("unchecked")
	public List<GnssRtkLog> findByRoverTagAndDate(String rovertag, String start, String end, int type, int status) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long starttime = sdf.parse(start).getTime();
		long endtime = sdf.parse(end).getTime();
		
		String hql = " from GnssRtkLog where rovertag = '"+rovertag+"' and updatetime>='"+starttime+"'  and updatetime <='"+endtime+"' ";
		if(-1!=type) {
			hql+= " and type="+type;
		}
		if(-1!=status) {
			hql+= " and status="+status;
		}
		hql += " order by updatetime ";
		List<GnssRtkLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<GnssRtkLog> findByMacAndDate(String mac, Date date, int start, int end, int i) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long starttime = sdf2.parse(sdf.format(date)+" "+getHour(start)+":00:00").getTime();
		long endtime = sdf2.parse(sdf.format(date)+" "+getHour(end)+":00:00").getTime();
		String hql = " from GnssRtkLog where rovertag = '"+mac+"' and updatetime>'"+starttime+"' and type="+i+" and updatetime <'"+endtime+"' ";
		List<GnssRtkLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public GnssRtkLog findByMacAndDate(String mac, String date, int start, int end, int i) throws ParseException {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long starttime = sdf2.parse(date+" "+getHour(start)+":00:00").getTime();
		long endtime = sdf2.parse(date+" "+getHour(end)+":00:00").getTime();
		String hql = " from GnssRtkLog where rovertag = '"+mac+"' and updatetime>'"+starttime+"' and type="+i+" and updatetime <'"+endtime+"' ";
		List<GnssRtkLog> list = em.createQuery(hql).getResultList();
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	private String getHour(int nowHour) {
		if(nowHour<10) {
			return "0"+nowHour;
		}
		return String.valueOf(nowHour);
	}
	
}
