package main.entry.webapp.data.project.front;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProGoods;
import database.models.project.ProOrder;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGoodsService;
import service.basicFunctions.project.ProOrderService;
import utils.Resp;

@RequestMapping(value = "/f/pro_order")
@Controller
public class FrontProOrderDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(FrontProOrderDataController.class);
	
	@Autowired
	private ProOrderService proOrderService;
	@Autowired
	private ProGoodsService ProGoodsService;
	
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String openid,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.getByOpenid(openid, p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> list(Integer id,String openid,String mobilePhone){
		Resp<?> resp = new Resp<>(false);
		try {
			ProGoods proGoods = ProGoodsService.findById(id);
			ProOrder proOrder = new ProOrder();
			proOrder.setCreateTime(new Date());
			proOrder.setDate(proGoods.getDate());
			proOrder.setMobilePhone(mobilePhone);
			proOrder.setName(proGoods.getName());
			proOrder.setOpenid(openid);
			proOrder.setPrice(proGoods.getPrice());
			proOrder.setStatus(0);
			proOrder.setType(proGoods.getAbc());
			proOrder.setTime(proGoods.getTime());
			proOrder.setFromSite(1);
			proOrder = proOrderService.saveOrder(proOrder);
			proGoods.setType(1);
			ProGoodsService.update(proGoods);
			return new Resp<>(proOrder);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	
	
	
}
