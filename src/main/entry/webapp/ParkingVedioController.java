package main.entry.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.ParkingSpace;
import database.models.ParkingVedio;
import service.basicFunctions.ParkingSpaceService;
import service.basicFunctions.ParkingVedioService;
import utils.model.Resp;

@Controller
public class ParkingVedioController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ParkingVedioController.class);
	
	@Autowired
	private ParkingVedioService parkingVedioService;
	@Autowired
	private ParkingSpaceService parkingSpacesService;
	
	@RequestMapping(value = "/d/saveVedio")
	@ResponseBody
	public Resp<?> insertVedio(String mac,String eventTime,Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
			ParkingSpace space = parkingSpacesService.findByMac(mac);
			ParkingVedio p = new ParkingVedio();
			Date date = sdf3.parse(eventTime);
			p.setCameraIndex(space.getCameraIndex());
			p.setChangeTime(eventTime);
			p.setCreateTime(new Date());
			p.setEventTime(eventTime);
			String filePath = "ftp://10.0.0.11/"+sdf.format(date)+"/"+mac+"_"+eventTime;
			if (status == 0) {
				filePath += "_outCarVideo_.mp4";
			} else {
				filePath += "_inCarVideo_.mp4";
			}
			p.setFilePath(filePath);
			p.setMac(mac);
			p.setSendStatus(0);
			p.setType(status);
			p.setUpdateStatus(0);
			p.setVedioStatus(1);
			parkingVedioService.save(p);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
