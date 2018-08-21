package main.entry.webapp.task;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.subcribe.SubcribeOrder;
import service.basicFunctions.subcribe.SubcribeOrderService;
import utils.msg.AlimsgUtils;

@Component
@Lazy(value=false)
public class SubcribeTask {


	@Autowired
	private SubcribeOrderService subcribeOrderService;
	
	@Scheduled(fixedRate = 1000 * 60 *30,initialDelay = 1000)
	public void init(){
		List<SubcribeOrder> list = subcribeOrderService.needSend();
		for(SubcribeOrder subcribeOrder : list){
			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if(subcribeOrder.getStartHour()-hour<=2&&subcribeOrder.getStartHour()-hour>0){
				AlimsgUtils.sendSubcibe(subcribeOrder.getMobilePhone(), "SMS_142615214",
						"展为",subcribeOrder.getParkName(),subcribeOrder.getDateStr()+" "+
				subcribeOrder.getStartHour()+"时~"+subcribeOrder.getEndHour()+"时 。");
				subcribeOrder.setNoticeType(2);
				subcribeOrderService.notice(subcribeOrder.getId(), 2);
			}
		}
		
		
     }
	
}
