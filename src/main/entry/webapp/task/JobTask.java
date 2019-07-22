package main.entry.webapp.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import service.basicFunctions.parking.ParkingAreaService;
import utils.msg.AlimsgUtils;

@Component
@Lazy(value = false)
public class JobTask {

	@Autowired
	private ParkingAreaService parkingAreaService;

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

}
