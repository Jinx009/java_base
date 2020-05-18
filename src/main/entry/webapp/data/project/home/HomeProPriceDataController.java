package main.entry.webapp.data.project.home;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProPrice;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProPriceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/h/pro_price")
public class HomeProPriceDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(HomeProPriceDataController.class);
	
	@Autowired
	private ProPriceService proPriceService;
	
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
	
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(Integer id,String time,String aName,String bName,String cName,String dName,double aPrice,double bPrice,double cPrice,double dPrice){
		Resp<?> resp = new Resp<>(false);
		try {
			ProPrice proPrice = proPriceService.findById(id);
			proPrice.setAName(aName);
			proPrice.setAPrice(aPrice);
			proPrice.setBName(bName);
			proPrice.setCName(cName);
			proPrice.setCPrice(cPrice);
			proPrice.setDName(dName);
			proPrice.setDPrice(dPrice);
			proPriceService.update(proPrice);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
}
