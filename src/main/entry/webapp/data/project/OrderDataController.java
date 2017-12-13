package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import main.entry.webapp.BaseController;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/order")
public class OrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(OrderDataController.class);
	
	/**
	 * pos订单信息
	 * @return
	 */
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> order(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = HttpData.order();
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
}
