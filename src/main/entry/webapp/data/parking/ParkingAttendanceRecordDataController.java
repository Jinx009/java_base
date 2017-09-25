package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingAttendanceRecord;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingAttendanceRecordService;
import utils.Resp;

/**
 * 考勤记录管理
 * @author jinx
 *
 */
@Controller
@RequestMapping(value = "/home/d")
public class ParkingAttendanceRecordDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingAttendanceRecordDataController.class);

	@Autowired
	private ParkingAttendanceRecordService parkingAttendanceRecordService;
	
	/**
	 * 获取所有考勤记录
	 * @return
	 */
	@RequestMapping(path = "/attendanceRecord")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingAttendanceRecord> list = parkingAttendanceRecordService.findAll();
			logger.warn("data:{}",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
