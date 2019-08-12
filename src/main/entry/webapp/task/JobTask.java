package main.entry.webapp.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;

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
import service.basicFunctions.parking.ParkingAreaService;
import service.basicFunctions.qj.QjDeviceLogService;
import service.basicFunctions.qj.QjNoticeService;
import service.basicFunctions.vedio.VedioLogService;
import service.basicFunctions.vedio.VedioTaskService;
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
	
	@Scheduled(fixedRate = 1000 * 3600, initialDelay = 1000)
	public void qjCheck() {
		List<QjNotice> list = qjNoticeService.findAll();
		log.warn("mac list:{}",JSONObject.toJSONString(list));
		for(QjNotice str:list){
			QjDeviceLog log = qjDeviceLogService.getNearBySn(str.getMac());
			if(log==null){
				AlimsgUtils.sendCheck(str.getMac(), "SMS_171565355", "展为","18217700275");
				AlimsgUtils.sendCheck(str.getMac(), "SMS_171565355", "展为","13918073897");
				AlimsgUtils.sendCheck(str.getMac(), "SMS_171565355", "展为","18108196835");
			}
		}
	}

	@Scheduled(fixedRate = 1000 * 3600, initialDelay = 1000)
	public void init() {
		Date parkingArea = parkingAreaService.find(9).getCreateTime();// vedio
		Date parkingArea2 = parkingAreaService.find(10).getCreateTime();// vedio
		Date parkingArea3 = parkingAreaService.find(11).getCreateTime();// vedio
		Date date = new Date();
		if (parkingArea == null) {
			AlimsgUtils.sendCheck("vedio", "SMS_171565355", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea.getTime())>(1000*3600)){
				AlimsgUtils.sendCheck("vedio", "SMS_171565355", "展为","18217700275");
			}
		}
		if (parkingArea2 == null) {
			AlimsgUtils.sendCheck("1.1", "SMS_171565355", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea2.getTime())>(1000*3600)){
				AlimsgUtils.sendCheck("1.1", "SMS_171565355", "展为","18217700275");
			}
		}
		if (parkingArea3 == null) {
			AlimsgUtils.sendCheck("1.4", "SMS_171565355", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea3.getTime())>(1000*3600)){
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
		AlimsgUtils.sendCheck("0009190600000042", "SMS_171565355", "展为","18217700275");
	}

}
