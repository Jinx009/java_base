package main.entry.webapp.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.vedio.VedioTask;
import service.basicFunctions.parking.ParkingAreaService;
import service.basicFunctions.vedio.VedioTaskService;
import utils.msg.AlimsgUtils;

@Component
@Lazy(value = false)
public class JobTask {
	
	private static final Logger log = LoggerFactory.getLogger(JobTask.class);
	

	@Autowired
	private ParkingAreaService parkingAreaService;
	@Autowired
	private VedioTaskService vedioTaskService;

	@Scheduled(fixedRate = 1000 * 3600, initialDelay = 1000)
	public void init() {
		Date parkingArea = parkingAreaService.find(9).getCreateTime();// vedio
		Date parkingArea2 = parkingAreaService.find(10).getCreateTime();// vedio
		Date parkingArea3 = parkingAreaService.find(11).getCreateTime();// vedio
		Date date = new Date();
		if (parkingArea == null) {
			AlimsgUtils.sendCheck("vedio", "SMS_140735042", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea.getTime())>(1000*3600)){
				AlimsgUtils.sendCheck("vedio", "SMS_140735042", "展为","18217700275");
			}
		}
		if (parkingArea2 == null) {
			AlimsgUtils.sendCheck("1.1", "SMS_140735042", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea2.getTime())>(1000*3600)){
				AlimsgUtils.sendCheck("1.1", "SMS_140735042", "展为","18217700275");
			}
		}
		if (parkingArea3 == null) {
			AlimsgUtils.sendCheck("1.4", "SMS_140735042", "展为","18217700275");
		} else {
			if((date.getTime()-parkingArea3.getTime())>(1000*3600)){
				AlimsgUtils.sendCheck("1.4", "SMS_140735042", "展为","18217700275");
			}
		}
	}
	
	/**
	 * 视频处理完成度
	 */
	@Scheduled(fixedRate = 1000 * 180, initialDelay = 1000)
	public void vedioCheck() {
		List<VedioTask> list = vedioTaskService.findByStatus(2);
		if(list!=null&&!list.isEmpty()){
			for(VedioTask vedioTask : list){
				String CMD = "cd "+vedioTask.getDirPath()+";python /zhanway/vehicle_demo.py 0.8 "+vedioTask.getDirPath()+"/"+vedioTask.getName()+".zip";
				 String[] cmd = { "sh", "-c",CMD };
		            log.warn("log:cmd:kill");
		            Process p;
					try {
						p = Runtime.getRuntime().exec(cmd);
						p.waitFor();
			            p.destroy();
			            vedioTask.setStatus(3);
			            File file = new File(vedioTask.getDirPath()+"/result.json");
			            StringBuilder result = new StringBuilder();
		                BufferedReader br = new BufferedReader(new FileReader(file));
		                String s = null;
		                while ((s = br.readLine()) != null) {
		                    result.append(s);
		                }
		                br.close();
		                vedioTask.setResult(result.toString());
		                vedioTaskService.update(vedioTask);
					} catch (Exception e) {
						log.error("e:{}",e);
					}
		            
			}
		}
	}

}
