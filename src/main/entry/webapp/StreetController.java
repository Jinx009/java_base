package main.entry.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.basicFunctions.StreetService;
import utils.model.Resp;

/**
 * 街道管理
 * @author jinx
 *
 */
@Controller
public class StreetController extends BaseController{

	private Logger LOG = LoggerFactory.getLogger(StreetController.class);
	
	@Autowired
	private StreetService streetService;
	
	/**
	 * 获取所有街道信息
	 * @return
	 */
	@RequestMapping(value = "/d/street/all")
	@ResponseBody
	public Resp<?> all(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(streetService.findAll());
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 街道列表
	 * @param p
	 * @return
	 */
	public Resp<?> pageList(int p ){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(streetService.findByPage(p));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
}
