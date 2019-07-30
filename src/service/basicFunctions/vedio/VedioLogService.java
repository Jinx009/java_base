package service.basicFunctions.vedio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.basicFunctions.dao.vedio.VedioAreaDao;
import database.basicFunctions.dao.vedio.VedioLogDao;
import database.models.vedio.VedioArea;
import database.models.vedio.VedioLog;
import database.models.vedio.VedioTask;

@Service
public class VedioLogService {

	@Autowired
	private VedioAreaDao vedioAreaDao;
	@Autowired
	private VedioLogDao vedioLogDao;

	public void insertVedioLog(VedioTask vedioTask) throws Exception {
		VedioArea vedioArea = vedioAreaDao.find(vedioTask.getAreaId());
		JSONObject jsonObject = JSONObject.parseObject(vedioTask.getResult());
		String date = vedioTask.getVedioStart();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = format.parse(date);
		for(int i = 0;i<vedioTask.getNum();i++){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.SECOND, 2);
			d = calendar.getTime();
			String s = jsonObject.getString("ffmpeg_"+(i+1)+".jpg");
			if(StringUtil.isNotBlank(s)){
				JSONObject obj = JSONObject.parseObject(s);
				insert(obj,0,vedioArea,vedioTask,format.format(d));
			}
		}
	}
	
	private void insert(JSONObject obj,int num,VedioArea area,VedioTask task,String d){
		String ss = obj.getString(""+num+"");
		if(StringUtil.isNotBlank(ss)){
			num++;
			VedioLog vedioLog = new VedioLog();
			vedioLog.setVedioTime(d);
			vedioLog.setTaskId(task.getId());
			vedioLog.setCreateTime(new Date());
			String carArry = JSONObject.parseObject(ss).getString("bbox");
			List<String> cArry = JSONObject.parseArray(carArry, String.class);
			vedioLog.setCarX(cArry.get(0));
			vedioLog.setCarX2(cArry.get(2));
			vedioLog.setCarY(cArry.get(1));
			vedioLog.setCarY2(cArry.get(3));
			vedioLog.setStatus(-1);
			vedioLog.setPicNumber(num);
			JSONArray jsonArray = JSONObject.parseArray( JSONObject.parseObject(ss).getString("chepai"));
			vedioLog.setCpX("");
			vedioLog.setCpX2("");
			vedioLog.setCpY("");
			vedioLog.setCpY2("");
			if(!jsonArray.isEmpty()){
				JSONObject j = jsonArray.getJSONObject(0);
				String cph = unicodeToCn(j.getString("text"));
				vedioLog.setCarNumber(cph);
				List<String> cpArry = JSONObject.parseArray(j.getString("bbox"), String.class);
				vedioLog.setCpX(cpArry.get(0));
				vedioLog.setCpX2(cpArry.get(2));
				vedioLog.setCpY(cpArry.get(1));
				vedioLog.setCpY2(cpArry.get(3));
				int x = (Integer.valueOf(vedioLog.getCpX())+Integer.valueOf(vedioLog.getCpX2()))/2;
				int y = (Integer.valueOf(vedioLog.getCpY())+Integer.valueOf(vedioLog.getCpY2()))/2;
				int x1 = Integer.valueOf(area.getX1())*2;
				int x3 = Integer.valueOf(area.getX3())*2;
				int y1 = Integer.valueOf(area.getY1())*2;
				int y3 = Integer.valueOf(area.getY3())*2;
				if(x>x1&&x<x3&&y<y1&&y>y3){
					vedioLog.setStatus(1);
				}else{
					vedioLog.setStatus(0);
				}
			}
			vedioLogDao.save(vedioLog);
			insert(obj, num,area,task,d);
		}
	}

	   public static String unicodeToCn(String s) {
		   String cph = "";
		   char[] c = s.toCharArray();
			for(int i = 0;i<c.length;i++){
				cph+=c[i];
			}
			return cph;
	    }
	
	public static void main(String[] args) {
		String s = "\u6caaJR3108";
		System.out.println(unicodeToCn(s));
	}

	public List<VedioLog> findByTaskId(Integer taskId) {
		return vedioLogDao.findByTaskId(taskId);
	}

	public List<VedioLog> findById(Integer id) {
		return vedioLogDao.findById(id);
	}

	
}
