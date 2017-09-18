package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import database.models.parking.ParkingNotice;
import service.basicFunctions.parking.ParkingNoticeService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingNotcieDataController {

	private static final Logger logger = LoggerFactory.getLogger(ParkingNotcieDataController.class);
	
	@Autowired
	private ParkingNoticeService parkingNoticeService;
	
	/**
	 * 保存一条新推送
	 * @param parkingNotice
	 * @return
	 */
	@RequestMapping(path = "/saveNotice")
	@ResponseBody
	public Resp<?> save(ParkingNotice parkingNotice){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("save data:{}",JSON.toJSONString(parkingNotice));
			parkingNoticeService.save(parkingNotice);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("save error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取所有推送
	 * @return
	 */
	@RequestMapping(path = "/notice")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingNotice> list = parkingNoticeService.findAll();
			logger.warn("list data:{}",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("list error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 更新推送状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(path = "/updateNotice")
	@ResponseBody
	public Resp<?> update(@RequestParam(value = "id",required = false)Integer id,
						  @RequestParam(value = "status",required = false)Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingNoticeService.updateStatus(id,status);
			logger.warn("update data:{},{}",id,status);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("update error:{}",e);
		}
		return resp;
	}
	
}
