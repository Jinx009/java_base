package main.entry.webapp;

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
	@RequestMapping(value = "/p/camera/add")
	public String add(){
		return "/page/camera_add";
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
