package main.entry.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.basicFunctions.VedioService;
import utils.model.Resp;

/**
 * 录制视频
 * @author jinx
 *
 */
@Controller
public class VedioController extends BaseController{

	private Logger LOG = LoggerFactory.getLogger(VedioController.class);
	
	@Autowired
	private VedioService vedioService;
	
	/**
	 * 录像列表页面
	 * @return
	 */
	@RequestMapping(value = "/p/vedio/list")
	public String list(){
		return "/page/vedio_list";
	}
	
	/**
	 * 新增录像页面
	 * @return
	 */
	@RequestMapping(value = "/p/vedio/add")
	public String add(){
		return "/page/vedio_add";
	}
	
	/**
	 * 录像任务列表
	 * @param p
	 * @param streetId
	 * @param parkNumber
	 * @return
	 */
	@RequestMapping(value = "/d/vedio/list")
	@ResponseBody
	public Resp<?> pageList(int p,Integer streetId,String parkNumber){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(vedioService.findByPage(p, streetId, parkNumber));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 新增录像任务
	 * @param streetId
	 * @param streetName
	 * @param cameraId
	 * @param cameraNumber
	 * @param parkNumber
	 * @param startTime
	 * @param endTime
	 * @param vedioName
	 * @return
	 */
	@RequestMapping(value = "/d/vedio/save")
	@ResponseBody
	public Resp<?> save(Integer streetId,String streetName,Integer cameraId,String cameraNumber,String parkNumber,String startTime,String endTime,String vedioName){
		Resp<?> resp = new Resp<>(false);
		try {
			vedioService.save(streetId, streetName, cameraId, cameraNumber, parkNumber, startTime, endTime, vedioName);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除某段录像
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/vedio/del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			vedioService.delete(id);
			return new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
}
