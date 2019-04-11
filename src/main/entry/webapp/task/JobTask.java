package main.entry.webapp.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.project.ProSwimSwitch;
import service.basicFunctions.service.ProSwimSwitchService;

@Component
@Lazy(value = false)
public class JobTask {

	private static final Logger logger = LoggerFactory.getLogger(JobTask.class);

	@Autowired
	private ProSwimSwitchService proSwimSwitchService;

	/**
	 * 提前一周设定
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void init() {
		logger.warn("start  swim switch job ...... ");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+7);
			Date date=calendar.getTime();
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if(week==0||week==6){
				String dateStr = sdf.format(date);
				ProSwimSwitch proSwimSwitch = proSwimSwitchService.findByDateStr(dateStr);
				if(proSwimSwitch==null){
					proSwimSwitchService.save(dateStr);
				}
			}
		} catch (Exception e) {
			logger.error("e:{}", e);
		}

	}

}
