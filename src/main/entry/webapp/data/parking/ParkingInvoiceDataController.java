package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingInvoice;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingInvoiceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingInvoiceDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ParkingInvoiceDataController.class);
	
	@Autowired
	private ParkingInvoiceService parkingInvoiceService;
	
	/**
	 * 获取所有发票信息
	 * @return
	 */
	@RequestMapping(path = "/invoice")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingInvoice> list = parkingInvoiceService.findAll();
			logger.warn("data:{}",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error",e);
		}
		return resp;
	}
	
	/**
	 * 更新发票发送状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(path = "/updateInvoiceStatus")
	@ResponseBody
	public Resp<?> changeStatus(Integer id,Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("datat:{},{}",id,status);
			parkingInvoiceService.updateStatus(id, status);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除一条发票
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/delInvoice")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("datat:{}",id);
			parkingInvoiceService.delete(id);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
