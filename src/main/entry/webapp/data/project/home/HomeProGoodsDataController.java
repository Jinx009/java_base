package main.entry.webapp.data.project.home;

import java.util.List;

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

@Controller
@RequestMapping(value  = "/h/pro_goods")
public class HomeProGoodsDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(HomeProGoodsDataController.class);
	
	@Autowired
	private ProGoodsService proGoodsService;
	@Autowired
	private ProOrderService proOrderService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String date){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proGoodsService.findByDate(date));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/del")
	@ResponseBody
	public Resp<?> del(int id){
		Resp<?> resp = new Resp<>(false);
		try {
			ProGoods proGoods = proGoodsService.findById(id);
			List<ProOrder> list = proOrderService.findByDateTimeType(proGoods.getDate(), proGoods.getTime(), proGoods.getAbc()) ;
			boolean b = true;
			if(list!=null&&!list.isEmpty()){
				for(ProOrder o : list){
					if(o.getStatus()!=2){
						b = false;
						break;
					}
				}
			}
			if(!b){
				resp.setMsg("请先删除相关订单！");
				return resp;
			}else{
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
