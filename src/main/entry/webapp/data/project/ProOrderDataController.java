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
import database.models.project.ProSwitch;
import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProOrderService;
import service.basicFunctions.service.ProSwitchService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/order")
public class ProOrderDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProOrderDataController.class);

	@Autowired
	private ProOrderService proOrderService;
	@Autowired
	private ProSwitchService proSwitchService;

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
	public Resp<?> save(String orderDate, Integer type, Integer userId, Integer userType, Integer num,String remark,Integer orderTime,Integer jlNum) {
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
			proOrderService.save(orderDate,type, userId, userType,  num, remark,  orderTime,jlNum);
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
							js+= 1;
						}
					}
				}
				str+= "水肺潜水：<font style='color:red;' >"+sf+"</font>人|";
				str+= "自由潜水：<font style='color:red;' >"+zy+"</font>人|";
				if(js<1){
					str+= "教室A：可预订|";
				}else{
					str+= "教室A：<font style='color:red;' >已预订</font>|";
				}
				if(js<2){
					str+= "教室B：可预订|";
				}else{
					str+= "教室B：<font style='color:red;' >已预订</font>|";
				}
				if(js<3){
					str+= "教室C：可预订|";
				}else{
					str+= "教室C：<font style='color:red;' >已预订</font>|";
				}
				map.put("sw", str);
			}
			proSwitch = proSwitchService.findByDateStr(dateStr, 2);
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
							js+= 1;
						}
					}
				}
				str+= "水肺潜水：<font style='color:red;' >"+sf+"</font>人|";
				str+= "自由潜水：<font style='color:red;' >"+zy+"</font>人|";
				if(js<1){
					str+= "教室A：可预订|";
				}else{
					str+= "教室A：<font style='color:red;' >已预订</font>|";
				}
				if(js<2){
					str+= "教室B：可预订|";
				}else{
					str+= "教室B：<font style='color:red;' >已预订</font>|";
				}
				if(js<3){
					str+= "教室C：可预订|";
				}else{
					str+= "教室C：<font style='color:red;' >已预订</font>|";
				}
				map.put("xw", str);
			}
			proSwitch = proSwitchService.findByDateStr(dateStr, 3);
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
							js+= 1;
						}
					}
				}
				str+= "水肺潜水：<font style='color:red;' >"+sf+"</font>人|";
				str+= "自由潜水：<font style='color:red;' >"+zy+"</font>人|";
				if(js<1){
					str+= "教室A：可预订|";
				}else{
					str+= "教室A：<font style='color:red;' >已预订</font>|";
				}
				if(js<2){
					str+= "教室B：可预订|";
				}else{
					str+= "教室B：<font style='color:red;' >已预订</font>|";
				}
				if(js<3){
					str+= "教室C：可预订|";
				}else{
					str+= "教室C：<font style='color:red;' >已预订</font>|";
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
		if(orderTime.equals("1")) {
			orderTime = "上午";
		}
		if(orderTime.equals("2")) {
			orderTime = "下午";
		}
		if(orderTime.equals("3")) {
			orderTime = "夜间";
		}
		Resp<?> resp = new Resp<>(false);
		Map<String, String> map = new HashMap<>();
		try {
			if ("3".equals(userType)||"2".equals(userType)) {//散客和会员
				List<ProOrder> zy = proOrderService.findOrder(userId, orderDate, orderTime, 1);
				List<ProOrder> sf = proOrderService.findOrder(userId, orderDate, orderTime, 2);
				int num = proOrderService.findOrderNum(orderDate, orderTime);
				map.put("js", "0");//0无权
				map.put("zyq", String.valueOf(num));
				map.put("sfq", String.valueOf(num));
				if(zy!=null&&!zy.isEmpty()){//已预约无权
					map.put("zyq", "0");
				}
				if(sf!=null&&!sf.isEmpty()){
					map.put("sfq", "0");
				}
				return new Resp<>(map);
			} 
			if ("1".equals(userType)) {//教练
				map.put("js", "3");//默认三间
				List<ProOrder> list = proOrderService.findOrder(orderDate, orderTime, 4);//教室
				List<ProOrder> list2 = proOrderService.findOrder(userId, orderDate, orderTime, 4);
				if (list != null && !list.isEmpty()) {
					map.put("js", String.valueOf(3-list.size()));
				}
				if(list2!=null&&!list2.isEmpty()){
					map.put("js", String.valueOf(0));
				}
				int num = proOrderService.findOrderNum(orderDate, orderTime);
				map.put("zyq", String.valueOf(num));
				map.put("sfq", String.valueOf(num));
				List<ProOrder> zy = proOrderService.findOrder(userId, orderDate, orderTime, 1);
				List<ProOrder> sf = proOrderService.findOrder(userId, orderDate, orderTime, 2);
				if(zy!=null&&!zy.isEmpty()){//已预约无权
					map.put("zyq", "0");
				}
				if(sf!=null&&!sf.isEmpty()){
					map.put("sfq", "0");
				}
				return new Resp<>(map);
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

}
