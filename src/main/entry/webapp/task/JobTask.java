package main.entry.webapp.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.qj.QjDeviceLog;
import database.models.qj.QjNotice;
import database.models.vedio.VedioTask;
import database.models.wenshidu.WenshiduDevice;
import database.models.wenshidu.WenshiduLog;
import service.basicFunctions.parking.ParkingAreaService;
import service.basicFunctions.qj.QjDeviceLogService;
import service.basicFunctions.qj.QjNoticeService;
import service.basicFunctions.vedio.VedioLogService;
import service.basicFunctions.vedio.VedioTaskService;
import service.basicFunctions.wenshidu.WenshiduDeviceService;
import service.basicFunctions.wenshidu.WenshiduLogService;
import utils.HttpUtils;
import utils.VedioUtils;
import utils.msg.AlimsgUtils;

@Component
@Lazy(value = false)
public class JobTask {
	
	private static final Logger log = LoggerFactory.getLogger(JobTask.class);
	

	@Autowired
	private ParkingAreaService parkingAreaService;
	@Autowired
	private VedioTaskService vedioTaskService;
	@Autowired
	private VedioLogService vedioLogService;
	@Autowired
	private QjDeviceLogService qjDeviceLogService;
	@Autowired
	private QjNoticeService qjNoticeService;
	@Autowired
	private WenshiduDeviceService wenshiduDeviceService;
	@Autowired
	private WenshiduLogService wenshiduLogService;
	
//	@Scheduled(cron = "0 0 8 * * ?")
	public void qjCheck() {
		List<QjNotice> list = qjNoticeService.findAll();
		log.warn("mac list:{}",JSONObject.toJSONString(list));
		for(QjNotice str:list){
			QjDeviceLog log = qjDeviceLogService.getNearBySn(str.getMac());
			if(log==null){
//				AlimsgUtils.sendCheck(str.getAddress()+str.getMac(), "SMS_180956741", "展为","13167262228");
				AlimsgUtils.sendCheckDevice(str.getAddress()+str.getMac(), "SMS_180956741", "展为","13918073897");
				AlimsgUtils.sendCheckDevice(str.getAddress()+str.getMac(), "SMS_180956741", "展为","18108196835");
			}
		}
	}
	
	@Scheduled(fixedRate = 1000 * 1200, initialDelay = 1000)
	public void wenshidu() {
		List<WenshiduDevice> list = wenshiduDeviceService.findAll();
		if(list!=null&&!list.isEmpty()){
			for(WenshiduDevice device : list){
				try {
					Thread.sleep(2000);
					String s = HttpUtils.get("http://open.sennor.net:8088/device/getDeviceInfo?secretKey=4498261B15CF4EEE&deviceNumber="+device.getDeviceNumber());
					JSONObject obj = JSONObject.parseObject(s);
					String data = obj.getString("data");
					WenshiduDevice d = JSONObject.parseObject(data, WenshiduDevice.class);
					Date d1 = device.getTime();
					Date d2 = d.getTime();
					if(d1==null||d2.after(d1)){
						device.setTime(d2);
						device.setData(d.getData());
//						device.setDeviceId(d.getDeviceId());
						device.setName(d.getName());
						device.setOnLineState(d.getOnLineState());
						wenshiduDeviceService.update(device);
						WenshiduLog log = new WenshiduLog();
						log.setData(d.getData());
						log.setDeviceNumber(d.getDeviceNumber());
						log.setName(d.getName());
						log.setTime(d2);
						wenshiduLogService.save(log);
						String[] ss = device.getData().split("\\|");
						String yl = "";
						if(ss[0].contains("m")){
							yl = ss[0].split("mm")[0];
						}
						if(ss[1].contains("m")){
							yl = ss[1].split("mm")[0];
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						WenshiduLog his = wenshiduLogService.findByDateAndNum(d.getDeviceNumber());
						if(his!=null){
							String[] ss1 = his.getData().split("\\|");
							String yl1 = "";
							if(ss1[0].contains("m")){
								yl1 = ss1[0].split("mm")[0];
							}
							if(ss1[1].contains("m")){
								yl1 = ss1[1].split("mm")[0];
							}
							Double yld = Double.valueOf(yl);
							Double yld1 = Double.valueOf(yl1);
							Double y = yld-yld1;
							if(y<0) {
								y=0.00;
							}
							Map< String, String> map = new HashMap<String, String>();
							map.put("sblxbm", "201");
							map.put("jczb", String.valueOf(y));
							map.put("jcsj", sdf.format(d2));
							map.put("cgq", "1");
							HttpUtils.sendPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+device.getDeviceId()+"&data="+JSONObject.toJSONString(map));
						}
					}
				} catch (Exception e) {
					log.error("e:{}",e);
				}
			}
		}
	}
	
	@Scheduled(fixedRate = 1000 * 3600, initialDelay = 1000)
	public void initVedio() {
		Date parkingArea = parkingAreaService.find(9).getCreateTime();// vedio
		Date date = new Date();
		if (parkingArea == null) {
			AlimsgUtils.sendCheck("vedio", "SMS_171565355", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea.getTime())>(1000*7200)){
				AlimsgUtils.sendCheck("vedio", "SMS_171565355", "展为","18217700275");
			}
		}
	}

	@Scheduled(fixedRate = 1000 * 600, initialDelay = 1000)
	public void init() {
		Date parkingArea2 = parkingAreaService.find(10).getCreateTime();// vedio
		Date parkingArea3 = parkingAreaService.find(11).getCreateTime();// vedio
		Date date = new Date();
		if (parkingArea2 == null) {
			AlimsgUtils.sendCheck("1.1", "SMS_171565355", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea2.getTime())>(1000*1200)){
				AlimsgUtils.sendCheck("1.1", "SMS_171565355", "展为","18217700275");
			}
		}
		if (parkingArea3 == null) {
			AlimsgUtils.sendCheck("1.4", "SMS_171565355", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea3.getTime())>(1000*1200)){
				AlimsgUtils.sendCheck("1.4", "SMS_171565355", "展为","18217700275");
			}
		}
	}
	
	/**
	 * 视频处理完成度
	 */
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000)
	public void vedioCheck2() {
		List<VedioTask> list = vedioTaskService.findByStatus(2);
		if(list!=null&&!list.isEmpty()){
			for(VedioTask vedioTask : list){
				File file = new File(vedioTask.getDirPath()+"/"+vedioTask.getName()+".zip");
				if(file.exists()){
					String CMD = "cd "+vedioTask.getDirPath()+";python3.6 /zhanway/vehicle_demo.py 0.8 "+vedioTask.getDirPath()+"/"+vedioTask.getName()+".zip";
					log.warn("cmd:{}",CMD);
					 String[] cmd = { "sh", "-c",CMD };
			            Process p;
						try {
							p = Runtime.getRuntime().exec(cmd);
							p.waitFor();
				            p.destroy();
				            vedioTask.setStatus(3);
			                vedioTaskService.update(vedioTask);
						} catch (Exception e) {
							log.error("e:{}",e);
						}
				}
			}
		}
	}
	
	/**
	 * 视频处理完成度ZIP
	 */
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000)
	public void vedioCheck() {
		List<VedioTask> list = vedioTaskService.findByStatus(1);
		if(list!=null&&!list.isEmpty()){
			for(VedioTask vedioTask : list){
				boolean r = true;
				for(int i = 1;i<=vedioTask.getNum();i++){
					File file = new File(vedioTask.getDirPath()+"/ffmpeg_"+i+".jpg");
					if(!file.exists()){
						r = false;
						break;
					}
				}
				if(r){
					File[] files = new File[vedioTask.getNum()];
					for(int i = 1;i<=vedioTask.getNum();i++){
						files[i-1] = new File(vedioTask.getDirPath()+"/ffmpeg_"+i+".jpg");
					}
					VedioUtils.zipFiles(files, new File(vedioTask.getDirPath()+"/"+vedioTask.getName()+".zip"));
					vedioTask.setStatus(2);
					vedioTaskService.update(vedioTask);
				}
			}
		}
	}
	
	
	/**
	 * 视频处理完成度
	 */
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000)
	public void vedioCheck3() {
		List<VedioTask> list = vedioTaskService.findByStatus(3);
		if(list!=null&&!list.isEmpty()){
			for(VedioTask vedioTask : list){
				File file = new File(vedioTask.getDirPath()+"/result.json");
				if(file.exists()){
						try {
				            StringBuilder result = new StringBuilder();
			                BufferedReader br = new BufferedReader(new FileReader(file));
			                String s = null;
			                while ((s = br.readLine()) != null) {
			                    result.append(s);
			                }
			                br.close();
			                String res = result.toString();
			                if(StringUtil.isNotBlank(res)){
			                	vedioTask.setResult(result.toString());
				                vedioTask.setStatus(4);
				                vedioTaskService.update(vedioTask);
				                insertLog(vedioTask);
			                }
						} catch (Exception e) {
							log.error("e:{}",e);
						}
				}
			}
		}
	}
	
	
	private void insertLog(VedioTask vedioTask){
		try {
			vedioLogService.insertVedioLog(vedioTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		AlimsgUtils.sendCheck("0009190600000042", "SMS_171565355", "展为","18217700275");
		WenshiduDevice device = new WenshiduDevice();
		device.setDeviceId("03111800013");
		device.setTime(new Date());
		device.setData("2.5 mm | 0 RSSI");
		Date d2 = device.getTime();
		String[] ss = device.getData().split("\\|");
		String yl = "";
		if(ss[0].contains("m")){
			yl = ss[0].split("mm")[0];
		}
		if(ss[1].contains("m")){
			yl = ss[1].split("mm")[0];
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map< String, String> map = new HashMap<String, String>();
		map.put("sblxbm", "201");
		map.put("jczb", yl);
		map.put("jcsj", sdf.format(d2));
		map.put("cgq", "1");
		HttpUtils.sendPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+device.getDeviceId()+"&data="+JSONObject.toJSONString(map));
	}

}
