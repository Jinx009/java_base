package main.entry.webapp.data.pro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class PosDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(PosDataController.class);

	@Autowired
	private HttpService httpService;
	
	/**
	 * pos列表
	 * @return
	 */
	@RequestMapping(path = "/pos")
	@ResponseBody
	public Resp<?> car(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get(HttpData.posUrl());
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	/**
	 * pos机账号
	 * @return
	 */
	@RequestMapping(path = "/account")
	@ResponseBody
	public Resp<?> account(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get(HttpData.accountUrl());
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	/**
	 * pos订单信息
	 * @return
	 */
	@RequestMapping(path = "/order")
	@ResponseBody
	public Resp<?> order(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = HttpData.order();
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	/**
	 * 车位对应关系信息列表
	 * @return
	 */
	@RequestMapping(path = "/place")
	@ResponseBody
	public Resp<?> place(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = HttpData.place();
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	/**
	 * 更新或新增地磁与车位对应关系
	 * @return
	 */
	@RequestMapping(path = "/insert")
	@ResponseBody
	public Resp<?> insert(@RequestParam(value = "mac",required = false)String mac,
					      @RequestParam(value = "code",required = false)String code,
					      @RequestParam(value = "place",required = false)String place,
					      @RequestParam(value = "remark",required = false)String remark){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = HttpData.insert(mac,code,place,remark);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
}
