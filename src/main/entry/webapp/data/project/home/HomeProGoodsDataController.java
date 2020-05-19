package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGoodsService;
import utils.Resp;

@Controller
@RequestMapping(value  = "/h/pro_goods")
public class HomeProGoodsDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(HomeProGoodsDataController.class);
	
	@Autowired
	private ProGoodsService proGoodsService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String date){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proGoodsService.findByDateH(date));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
