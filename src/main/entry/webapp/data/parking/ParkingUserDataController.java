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
import database.models.parking.ParkingUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingMonthCardService;
import service.basicFunctions.parking.ParkingUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingUserDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ParkingUserDataController.class);
	
	@Autowired
	private ParkingUserService parkingUserService;
	@Autowired
	private ParkingMonthCardService parkingMonthCardService;

	/**
	 * 获取所有会员信息
	 * @return
	 */
	@RequestMapping(path = "/user")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingUser> list = parkingUserService.findAll();
			logger.warn("data:{}",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 拉黑与被拉黑
	 * @param id
	 * @param black
	 * @return
	 */
	@RequestMapping(path = "/updateUserBlack")
	@ResponseBody
	public Resp<?> blackList(Integer id,Integer black){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{},{}",id,black);
			parkingUserService.setBlackList(id,black);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 设置特别车辆
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "updateUserSpecial")
	@ResponseBody
	public Resp<?> setSpecial(Integer id,Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{}",id);
			parkingUserService.setSpecial(id,status);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}

	/**
	 * 设置包月会员
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "updateUserMonth")
	@ResponseBody
	public Resp<?> setMonth(Integer id,Integer monthId,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{},{}",id,monthId);
			if(parkingMonthCardService.checkUse(monthId)){
				HomeUser homeUser = getSessionHomeUser(req);
				parkingUserService.setMonth(id,monthId,homeUser);
				resp = new Resp<>("");
				return resp;
			}else{
				resp.setMsg("该序列号已经被使用！");
				return resp;
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
