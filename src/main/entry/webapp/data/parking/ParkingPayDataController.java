package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import database.models.parking.ParkingPay;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingPayService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingPayDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ParkingPayDataController.class);
	
	@Autowired
	private ParkingPayService parkingPayService;
	
	/**
	 * 获取所有支付方式
	 * @return
	 */
	@RequestMapping(path = "/pay")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingPay> list = parkingPayService.findAll();
			logger.warn("data:{}",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 新建支付渠道
	 * @param parkingPay
	 * @return
	 */
	@RequestMapping(path = "/savePay")
	@ResponseBody
	public Resp<?> save(ParkingPay parkingPay){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{}",JSON.toJSONString(parkingPay));
			parkingPayService.save(parkingPay);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 更改渠道状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(path = "/updatePayStatus")
	@ResponseBody
	public Resp<?> updateStatus(Integer id,Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{},{}",id,status);
			parkingPayService.changeStatus(id, status);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/delPay")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("data:{}",id);
			parkingPayService.delete(id);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
}
