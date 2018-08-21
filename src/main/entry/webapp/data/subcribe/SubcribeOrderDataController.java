package main.entry.webapp.data.subcribe;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.subcribe.SubcribeParkPlace;
import database.models.subcribe.SubcribeUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.subcribe.SubcribeOrderService;
import service.basicFunctions.subcribe.SubcribeParkPlaceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/subcribe/d/order")
public class SubcribeOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(SubcribeOrderDataController.class);
	
	@Autowired
	private SubcribeOrderService subcribeOrderService;
	@Autowired
	private SubcribeParkPlaceService subcribeParkPlaceService;

	@RequestMapping(path = "left")
	@ResponseBody
	public Resp<?> getLeft(Integer parkId,Integer startHour,Integer endHour,String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			SubcribeParkPlace subcribeParkPlace = subcribeParkPlaceService.findById(parkId);
			int left = subcribeOrderService.left(parkId, dateStr, startHour, endHour);
			Map<String, Integer> map = new HashMap<>();
			map.put("total", subcribeParkPlace.getNumber());
			map.put("left", left);
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(Integer parkId,Integer startHour,Integer endHour,String dateStr,String plateNumber,String mobilePhone){
		Resp<?> resp = new Resp<>(false);
		try {
			int left = subcribeOrderService.getUseByPlateNumber(parkId, dateStr, startHour, endHour, plateNumber);
			if(left>0){
				resp.setMsg("预定冲突，存在相同车牌相同时间预定！");
				return resp;
			}else{
				subcribeOrderService.save(mobilePhone, startHour, endHour, parkId, plateNumber, dateStr);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "del")
	@ResponseBody
	public Resp<?> save(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			subcribeOrderService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "notice")
	@ResponseBody
	public Resp<?> notice(Integer id,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			subcribeOrderService.notice(id,type);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			HttpSession session = request.getSession();
			SubcribeUser subcribeUser = (SubcribeUser) session.getAttribute("subcribeUser");
			return new Resp<>(subcribeOrderService.findByMobilePhone(subcribeUser.getMobilePhone()));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
