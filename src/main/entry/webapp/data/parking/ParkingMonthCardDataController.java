package main.entry.webapp.data.parking;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeUser;
import database.models.parking.ParkingMonthCard;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingMonthCardService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingMonthCardDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ParkingMonthCardDataController.class);
	
	@Autowired
	private ParkingMonthCardService parkingMonthCardService;
	
	/**
	 * 获取所有包月卡
	 * @return
	 */
	@RequestMapping(path = "/monthCard")
	@ResponseBody
	public Resp<?> findAll(Integer status,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingMonthCard> list = parkingMonthCardService.findAll(status,type);
			logger.warn("data:{}",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 新建一张包月卡
	 * @param parkingMonthCard
	 * @param req
	 * @return
	 */
	@RequestMapping(path = "/saveMonthCard")
	@ResponseBody
	public Resp<?> save(ParkingMonthCard parkingMonthCard,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{}",parkingMonthCard);
			HomeUser homeUser = getSessionHomeUser(req);
			parkingMonthCardService.save(parkingMonthCard, homeUser.getId());
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除一条包月卡记录
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/delMonthCard")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{}",id);
			parkingMonthCardService.delete(id);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 更新包月卡状态
	 * @param id
	 * @param userId
	 * @param req
	 * @return
	 */
	@RequestMapping(path = "/updateMonthCardStatus")
	@ResponseBody
	public Resp<?> updateStatus(Integer id,Integer userId,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{},{}",id,userId);
			HomeUser homeUser = getSessionHomeUser(req);
			parkingMonthCardService.updateStatus(id, homeUser.getId(), userId);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
