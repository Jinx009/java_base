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
	 * 街道页面
	 * @return
	 */
	@RequestMapping(value ="/p/street/list")
	public String list(){
		return "/page/street_list";
	}
	
	
	/**
	 * 街道编辑页面
	 * @return
	 */
	@RequestMapping(value ="/p/street/edit")
	public String edit(){
		return "/page/street_edit";
	}
	
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
	 * 获取单个街道信息
	 * @return
	 */
	@RequestMapping(value = "/d/street/find")
	@ResponseBody
	public Resp<?> find(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(streetService.findById(id));
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
	@RequestMapping(value ="/d/street/list")
	@ResponseBody
	public Resp<?> pageList(int p ){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(streetService.findByPage(p));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 新建街道
	 * @param name
	 * @param parkTotal
	 * @param streetNumber
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/d/street/save")
	@ResponseBody
	public Resp<?> save(String name,Integer parkTotal,String streetNumber,String address){
		Resp<?> resp = new Resp<>(false);
		try {
			streetService.save(streetNumber, address, parkTotal, name);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 编辑街道
	 * @param id
	 * @param address
	 * @param parkTotal
	 * @param streetNumber
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/d/street/edit")
	@ResponseBody
	public Resp<?> edit(Integer id,String address,Integer parkTotal,String streetNumber,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			streetService.update(id, streetNumber, address, parkTotal, name);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除街道
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/street/del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			streetService.delete(id);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
}
