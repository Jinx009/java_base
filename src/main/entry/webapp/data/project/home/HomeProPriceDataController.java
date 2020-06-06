package main.entry.webapp.data.project.home;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProGoods;
import database.models.project.ProPrice;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGoodsService;
import service.basicFunctions.project.ProPriceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/h/pro_price")
public class HomeProPriceDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(HomeProPriceDataController.class);
	
	@Autowired
	private ProPriceService proPriceService;
	@Autowired
	private ProGoodsService proGoodsService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proPriceService.findAll());
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/findByTime")
	@ResponseBody
	public Resp<?> findByTime(String time){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proPriceService.findByTime(time));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(Integer id,String name,double price){
		Resp<?> resp = new Resp<>(false);
		try {
			ProPrice proPrice = proPriceService.findById(id);
			proPrice.setName(name);
			proPrice.setPrice(price);
			proPriceService.update(proPrice);
			List<ProGoods> list = proGoodsService.findByDateUpdate(proPrice.getTime(),proPrice.getName());
			if(list!=null) {
				for(ProGoods proGoods:list) {
					if(proGoods.getType()==0) {
						proGoods.setPrice(price);
						proGoodsService.update(proGoods);
					}
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
}
