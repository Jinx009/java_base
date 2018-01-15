package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import service.basicFunctions.product.ProductService;
import utils.BaseConstant;
import utils.Resp;

@RequestMapping(value = "/interface/product")
@Controller
public class InterfaceProductDataController {

	private static final Logger log = LoggerFactory.getLogger(InterfaceProductDataController.class);

	@Autowired
	private ProductService productService;

	@RequestMapping(path = "/get")
	@ResponseBody
	public Resp<?> get() {
		Resp<?> resp = new Resp<>(false);
		try {
			String result = productService.getStatus();
			return new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG, result);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/set")
	@ResponseBody
	public Resp<?> set(String status) {
		Resp<?> resp = new Resp<>(false);
		try {
			String result = productService.setStatus(status);
			return new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG, result);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/set/light")
	@ResponseBody
	public Resp<?> setLight() {
		Resp<?> resp = new Resp<>(false);
		try {
			String result = productService.setLight();
			return new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG, result);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/set/PM2_5")
	@ResponseBody
	public Resp<?> setPM2_5() {
		Resp<?> resp = new Resp<>(false);
		try {
			String result = productService.setPM2_5();
			return new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG, result);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

}
