package main.entry.webapp;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.basicFunctions.ParkingSpaceService;
import utils.model.Resp;

/**
 * 停车位管理
 * @author jinx
 *
 */
@Controller
public class ParkingSpaceController extends BaseController{

	private Logger LOG = LoggerFactory.getLogger(ParkingSpaceController.class);
	
	@Autowired
	private ParkingSpaceService parkingSpaceService;
	
	/**
	 * 停车位列表页面
	 * @return
	 */
	@RequestMapping(value = "/p/space/list")
	public String list(){
		return "/page/space_list";
	}
	
	/**
	 * 编辑停车位页面
	 * @return
	 */
	@RequestMapping(value = "/p/space/edit")
	public String edit(){
		return "/page/space_edit";
	}
	
	/**
	 * 停车位列表
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/d/space/list")
	@ResponseBody
	public Resp<?> pageList(int p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(parkingSpaceService.findByPage(p));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 修改停车位信息
	 * @param id
	 * @param name
	 * @param streetId
	 * @param status
	 * @param cameraId
	 * @param cameraNumber
	 * @param happenTime
	 * @return
	 */
	@RequestMapping(value = "/d/space/update")
	@ResponseBody
	public Resp<?> update(Integer id,String name,Integer streetId,Integer status,Integer cameraId,String cameraNumber,Date happenTime){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingSpaceService.update(id, name, streetId, status, cameraId, cameraNumber, happenTime);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除停车位
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/space/del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingSpaceService.delete(id);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取单个停车位
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/space/find")
	@ResponseBody
	public Resp<?> find(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(parkingSpaceService.find(id));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
}
