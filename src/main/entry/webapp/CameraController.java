package main.entry.webapp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.basicFunctions.CameraService;
import utils.model.Resp;

/**
 * 抓拍机
 * @author jinx
 *
 */
@Controller
public class CameraController extends BaseController{
	
	private Logger LOG = LoggerFactory.getLogger(CameraController.class);

	@Autowired
	private CameraService cameraService;
	
	/**
	 * 抓拍机列表页面
	 * @return
	 */
	@RequestMapping(value = "/p/camera/list")
	public String page(){
		return "/page/camera_list";
	}
	
	/**
	 * 抓拍机新增页面
	 * @return
	 */
	@RequestMapping(value = "/p/camera/edit")
	public String edit(HttpServletRequest req,Integer id){
		req.setAttribute("id", id);
		return "/page/camera_edit";
	}
	
	/**
	 * 抓拍机列表
	 * @param streetId
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/d/camera/list")
	@ResponseBody
	public Resp<?> pageList(Integer streetId,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(cameraService.findByPage(streetId, p));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取所有相机
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/d/camera/all")
	@ResponseBody
	public Resp<?> all(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(cameraService.findAll());
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 查询单个相机
	 * @param streetId
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/camera/find")
	@ResponseBody
	public Resp<?> find(Integer streetId,Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(cameraService.find(id));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 新增抓拍机
	 * @param deviceNumber
	 * @param streetId
	 * @param streetName
	 * @param parkNumber
	 * @param deviceType
	 * @return
	 */
	@RequestMapping(value = "/d/camera/save")
	@	ResponseBody
	public  Resp<?> save(String deviceNumber,Integer streetId,String streetName,String parkNumber,String deviceType){
		Resp<?> resp = new Resp<>(false);
		try {
			cameraService.save(deviceNumber, streetId, streetName, parkNumber, deviceType);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除抓拍机
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/camera/del")
	@ResponseBody
	public Resp<?> delete(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			cameraService.delete(id);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
}
