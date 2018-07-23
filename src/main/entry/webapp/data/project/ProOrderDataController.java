package main.entry.webapp.data.project;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProOrderService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/order")
public class ProOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProOrderDataController.class);
	
	@Autowired
	private ProOrderService proOrderService;
	
	@RequestMapping(path = "/pageList")
	@ResponseBody
	public Resp<?> getClass(String orderDate,Integer p,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.homeList(orderDate, p,type));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/saveRemark")
	@ResponseBody
	public Resp<?> saveRemark(Integer id,String remark,Integer realNum){
		Resp<?> resp = new Resp<>(true);
		try {
			proOrderService.saveRemark(id,remark,realNum);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String orderDate,Integer type,Integer userId,Integer orderType,Integer num){
		Resp<?> resp = new Resp<>(true);
		try {
			proOrderService.save(orderDate, type,userId,orderType,num);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/myOrder")
	@ResponseBody
	public Resp<?> myOrder(Integer userId){
		Resp<?> resp = new Resp<>(true);
		try {
			return new Resp<>(proOrderService.myOrder(userId));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/getStatus")
	@ResponseBody
	public Resp<?> getStatus(String date,Integer type,String userId){
		Resp<?> resp = new Resp<>(true);
		try {
			Integer a = proOrderService.getStatus(date,type,userId,"上午");
			Integer b = proOrderService.getStatus(date,type,userId,"下午");
			Integer c = proOrderService.getStatus(date,type,userId,"夜间");
			Map<String, Integer> map = new HashMap<String,Integer>();
			map.put("a",a);
			map.put("b",b);
			map.put("c",c);
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/getSwimmingStatus")
	@ResponseBody
	public Resp<?> getSwimmingStatus(String date,Integer type,String userId){
		Resp<?> resp = new Resp<>(true);
		try {
			Integer a = proOrderService.getStatus(date,type,userId,"09:00:00~10:30:00");
			Integer b = proOrderService.getStatus(date,type,userId,"10:30:00~12:00:00");
			Integer c = proOrderService.getStatus(date,type,userId,"12:00:00~13:30:00");
			Integer d = proOrderService.getStatus(date,type,userId,"13:30:00~15:00:00");
			Integer e = proOrderService.getStatus(date,type,userId,"15:00:00~16:30:00");
			Integer f = proOrderService.getStatus(date,type,userId,"16:30:00~18:00:00");
			Integer g = proOrderService.getStatus(date,type,userId,"18:00:00~19:30:00");
			Integer h = proOrderService.getStatus(date,type,userId,"19:30:00~21:00:00");
			Map<String, Integer> map = new HashMap<String,Integer>();
			map.put("a",a);
			map.put("b",b);
			map.put("c",c);
			map.put("d",d);
			map.put("e",e);
			map.put("f",f);
			map.put("g",g);
			map.put("h",h);
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
