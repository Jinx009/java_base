package main.entry.webapp.data.project.home;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProGoods;
import database.models.project.ProOrder;
import database.models.project.ProPrice;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGoodsService;
import service.basicFunctions.project.ProOrderService;
import service.basicFunctions.project.ProPriceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/h/pro_order")
public class HomeProOrderDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(HomeProOrderDataController.class);

	@Autowired
	private ProOrderService proOrderService;
	@Autowired
	private ProPriceService proPriceService;
	@Autowired
	private ProGoodsService proGoodsService;

	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> list(Integer id, String date, String mobilePhone) {
		Resp<?> resp = new Resp<>(false);
		try {
			ProPrice proPrice = proPriceService.findById(id);
			List<ProOrder> proOrders = proOrderService.findByDateTimeType(date, proPrice.getTime(), proPrice.getType());
			boolean b = true;
			if (proOrders != null && !proOrders.isEmpty()) {
				for (ProOrder proOrder : proOrders) {
					if (-1 != proOrder.getStatus()) {
						b = false;
						break;
					}
				}
			}
			if (!b) {
				resp.setMsg("该场次已被占用！");
				return resp;
			} else {
				ProOrder proOrder = new ProOrder();
				proOrder = new ProOrder();
				proOrder.setCreateTime(new Date());
				proOrder.setDate(date);
				proOrder.setFromSite(2);
				proOrder.setMobilePhone(mobilePhone);
				proOrder.setName(proPrice.getName());
				proOrder.setPrice(proPrice.getPrice());
				proOrder.setOpenid("");
				proOrder.setPrice(proOrder.getPrice());
				proOrder.setStatus(1);
				proOrder.setType(proPrice.getType());
				proOrder.setTime(proPrice.getTime());
				proOrderService.saveOrder(proOrder);
				ProGoods proGoods = proGoodsService.findByTimeDateAbc(proPrice.getTime(), proPrice.getType(), date);
				if (proGoods != null) {
					proGoods.setType(2);
					proGoodsService.update(proGoods);
				}
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> getOrder(String fromDate, String toDate, Integer fromSite, Integer p) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.findPage(fromSite, fromDate, toDate, p));
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}
}
