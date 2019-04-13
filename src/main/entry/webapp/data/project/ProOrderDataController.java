package main.entry.webapp.data.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProOrder;
import database.models.project.ProPhoto;
import database.models.project.ProSwimSwitch;
import database.models.project.ProSwitch;
import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProOrderService;
import service.basicFunctions.service.ProPhotoService;
import service.basicFunctions.service.ProSwimSwitchService;
import service.basicFunctions.service.ProSwitchService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/order")
public class ProOrderDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProOrderDataController.class);

	@Autowired
	private ProOrderService proOrderService;
	@Autowired
	private ProSwimSwitchService proSwimSwitchService;
	@Autowired
	private ProSwitchService proSwitchService;
	@Autowired
	private ProPhotoService proPhotoService;

	@RequestMapping(path = "/pageList")
	@ResponseBody
	public Resp<?> getClass(String orderDate, Integer p, Integer type) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.homeList(orderDate, p, type));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/saveRemark")
	@ResponseBody
	public Resp<?> saveRemark(Integer id, String remark, Integer realNum) {
		Resp<?> resp = new Resp<>(true);
		try {
			proOrderService.saveRemark(id, remark, realNum);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	/**
	 * 2019-04-11
	 * 
	 * @param orderDate
	 * @param type
	 * @param userId
	 * @param orderType
	 * @param num
	 * @return
	 */
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String orderDate, Integer type, Integer userId, Integer userType, Integer num,String remark,Integer orderTime) {
		Resp<?> resp = new Resp<>(true);
		try {
			String time = "";
			if(orderTime.equals("1")) {
				time = "上午";
			}
			if(orderTime.equals("2")) {
				time = "下午";
			}
			if(orderTime.equals("3")) {
				time = "夜间";
			}
			List<ProOrder> proOrder = proOrderService.findOrder(userId, orderDate, time, type);
			if(proOrder!=null&&!proOrder.isEmpty()) {
				resp.setMsg("不能重复预定！");
				return resp;
			}
			proOrderService.save(orderDate,type, userId, userType,  num, remark,  orderTime);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/myOrder")
	@ResponseBody
	public Resp<?> myOrder(Integer userId) {
		Resp<?> resp = new Resp<>(true);
		try {
			return new Resp<>(proOrderService.myOrder(userId));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	// @RequestMapping(path = "/getStatus")
	// @ResponseBody
	// public Resp<?> getStatus(String date,Integer type,String userId){
	// Resp<?> resp = new Resp<>(true);
	// try {
	// Integer a = proOrderService.getStatus(date,type,userId,"上午");
	// Integer b = proOrderService.getStatus(date,type,userId,"下午");
	// Integer c = proOrderService.getStatus(date,type,userId,"夜间");
	// Map<String, Integer> map = new HashMap<String,Integer>();
	// map.put("a",a);
	// map.put("b",b);
	// map.put("c",c);
	// return new Resp<>(map);
	// } catch (Exception e) {
	// log.error("error:{}",e);
	// }
	// return resp;
	// }

	// @RequestMapping(path = "/getSwimmingStatus")
	// @ResponseBody
	// public Resp<?> getSwimmingStatus(String date,Integer type,String userId){
	// Resp<?> resp = new Resp<>(true);
	// try {
	// Integer a =
	// proOrderService.getStatus(date,type,userId,"09:00:00~10:30:00");
	// Integer b =
	// proOrderService.getStatus(date,type,userId,"10:30:00~12:00:00");
	// Integer c =
	// proOrderService.getStatus(date,type,userId,"12:00:00~13:30:00");
	// Integer d =
	// proOrderService.getStatus(date,type,userId,"13:30:00~15:00:00");
	// Integer e =
	// proOrderService.getStatus(date,type,userId,"15:00:00~16:30:00");
	// Integer f =
	// proOrderService.getStatus(date,type,userId,"16:30:00~18:00:00");
	// Integer g =
	// proOrderService.getStatus(date,type,userId,"18:00:00~19:30:00");
	// Integer h =
	// proOrderService.getStatus(date,type,userId,"19:30:00~21:00:00");
	// Map<String, Integer> map = new HashMap<String,Integer>();
	// map.put("a",a);
	// map.put("b",b);
	// map.put("c",c);
	// map.put("d",d);
	// map.put("e",e);
	// map.put("f",f);
	// map.put("g",g);
	// map.put("h",h);
	// return new Resp<>(map);
	// } catch (Exception e) {
	// log.error("error:{}",e);
	// }
	// return resp;
	// }

	@RequestMapping(path = "/base")
	@ResponseBody
	public Resp<?> getBase(String dateStr) {
		Resp<?> resp = new Resp<>(false);
		Map<String, String> map = new HashMap<>();
		map.put("sw", "1");
		map.put("xw", "1");
		map.put("yj", "1");
		try {
			ProSwitch proSwitch = proSwitchService.findByDateStr(dateStr, 1);
			ProPhoto proPhoto = proPhotoService.findByDateStr(dateStr,1);
			if(proSwitch!=null){
				map.put("sw", "#|"+proSwitch.getReason());
			}else{
				String str = "*|";
				int sf = 0;
				int zy = 0;
				int js = 0;
				List<ProOrder> list = proOrderService.findOrderByTime(dateStr, "上午");
				if(list!=null&&!list.isEmpty()){
					for(ProOrder proOrder:list){
						if(proOrder.getType()==1){
							zy+= proOrder.getNum();
						}
						if(proOrder.getType()==2){
							sf+= proOrder.getNum();
						}
						if(proOrder.getType()==4){
							js+= proOrder.getNum();
						}
					}
				}
				str+= "水肺潜水：<font style='color:red;' >"+sf+"</font>人|";
				if(js<1){
					str+= "教室A：可预订|";
				}else{
					str+= "教室A：<font style='color:red;' >已预订</font>|";
				}
				str+= "自由潜水：<font style='color:red;' >"+zy+"</font>人|";
				if(js<2){
					str+= "教室B：可预订|";
				}else{
					str+= "教室B：<font style='color:red;' >已预订</font>|";
				}
				if(proPhoto!=null){
					str+= "水下摄像：已预订";
				}else{
					str+= "水下摄像：可预订";
				}
				map.put("sw", str);
			}
			proSwitch = proSwitchService.findByDateStr(dateStr, 2);
			proPhoto = proPhotoService.findByDateStr(dateStr,2);
			if(proSwitch!=null){
				map.put("xw", "#|"+proSwitch.getReason());
			}else{
				String str = "*|";
				int sf = 0;
				int zy = 0;
				int js = 0;
				List<ProOrder> list = proOrderService.findOrderByTime(dateStr, "下午");
				if(list!=null&&!list.isEmpty()){
					for(ProOrder proOrder:list){
						if(proOrder.getType()==1){
							zy+= proOrder.getNum();
						}
						if(proOrder.getType()==2){
							sf+= proOrder.getNum();
						}
						if(proOrder.getType()==4){
							js+= proOrder.getNum();
						}
					}
				}
				str+= "水肺潜水：<font style='color:red;' >"+sf+"</font>人|";
				if(js<1){
					str+= "教室A：可预订|";
				}else{
					str+= "教室A：<font style='color:red;' >已预订</font>|";
				}
				str+= "自由潜水：<font style='color:red;' >"+zy+"</font>人|";
				if(js<2){
					str+= "教室B：可预订|";
				}else{
					str+= "教室B：<font style='color:red;' >已预订</font>|";
				}
				if(proPhoto!=null){
					str+= "水下摄像：已预订";
				}else{
					str+= "水下摄像：可预订";
				}
				map.put("xw", str);
			}
			proSwitch = proSwitchService.findByDateStr(dateStr, 3);
			proPhoto = proPhotoService.findByDateStr(dateStr,3);
			if(proSwitch!=null){
				map.put("yj", "#|"+proSwitch.getReason());
			}else{
				String str = "*|";
				int sf = 0;
				int zy = 0;
				int js = 0;
				List<ProOrder> list = proOrderService.findOrderByTime(dateStr, "夜间");
				if(list!=null&&!list.isEmpty()){
					for(ProOrder proOrder:list){
						if(proOrder.getType()==1){
							zy+= proOrder.getNum();
						}
						if(proOrder.getType()==2){
							sf+= proOrder.getNum();
						}
						if(proOrder.getType()==4){
							js+= proOrder.getNum();
						}
					}
				}
				str+= "水肺潜水：<font style='color:red;' >"+sf+"</font>人|";
				if(js<1){
					str+= "教室A：可预订|";
				}else{
					str+= "教室A：<font style='color:red;' >已预订</font>|";
				}
				str+= "自由潜水：<font style='color:red;' >"+zy+"</font>人|";
				if(js<2){
					str+= "教室B：可预订|";
				}else{
					str+= "教室B：<font style='color:red;' >已预订</font>|";
				}
				if(proPhoto!=null){
					str+= "水下摄像：已预订|";
				}else{
					str+= "水下摄像：可预订|";
				}
				ProSwimSwitch proSwimSwitch = proSwimSwitchService.findByDateStr(dateStr);
				if(proSwimSwitch!=null){
					str+= "游泳大班不可预订";
				}else{
					str+= "游泳大班对外开放";
				}
				map.put("yj", str);
			}
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

	/**
	 * 2019-04-11
	 * 
	 * @param userId
	 * @param orderDate
	 * @param orderTime
	 * @param userType
	 * @return
	 */
	@RequestMapping(path = "/getButtons")
	@ResponseBody
	public Resp<?> getButtons(Integer userId, String orderDate, String orderTime, String userType) {
		Resp<?> resp = new Resp<>(false);
		Map<String, String> map = new HashMap<>();
		map.put("zyq", "0");
		map.put("sfq", "0");
		map.put("yx", "0");
		map.put("js", "0");
		try {
			if ("5".equals(userType)) {
				return new Resp<>(map);
			} 
			if ("4".equals(userType)) {
				map.put("zyq", "1");
				List<ProOrder> list = proOrderService.findOrder(userId, orderDate, orderTime, 1);
				if (list != null && !list.isEmpty()) {
					map.put("zyq", "0");
				}
				return new Resp<>(map);
			} 
			if ("3".equals(userType)) {
				map.put("sfq", "1");
				List<ProOrder> list = proOrderService.findOrder(userId, orderDate, orderTime, 2);
				if (list != null && !list.isEmpty()) {
					map.put("sfq", "0");
				}
				return new Resp<>(map);
			} 
			if ("2".equals(userType)) {
				map.put("yx", "1");
				List<ProOrder> list = proOrderService.findOrder(userId, orderDate, orderTime, 3);
				if (list != null && !list.isEmpty()) {
					map.put("yx", "0");
				}
				return new Resp<>(map);
			} 
			if ("1".equals(userType)) {
				map.put("zyq", "1");
				map.put("sfq", "1");
				map.put("js", "1");
				List<ProOrder> list = proOrderService.findOrder(orderDate, orderTime, 4);
				if (list != null && !list.isEmpty()) {
					if (list.size() == 2) {
						map.put("js", "0");
					}
					for (ProOrder proOrder : list) {
						if (proOrder.getUserId() == userId) {
							map.put("zyq", "0");
							map.put("sfq", "0");
							map.put("js", "0");
							break;
						}
					}
				}
				List<ProOrder> list2 = proOrderService.findOrder(userId, orderDate, orderTime, 2);
				if (list2 != null && !list2.isEmpty()) {
					map.put("zyq", "0");
					map.put("sfq", "0");
					map.put("js", "0");
				}
				List<ProOrder> list3 = proOrderService.findOrder(userId, orderDate, orderTime, 1);
				if (list3 != null && !list3.isEmpty()) {
					map.put("zyq", "0");
					map.put("sfq", "0");
					map.put("js", "0");
				}
				return new Resp<>(map);
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

}
