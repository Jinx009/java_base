package main.entry.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.basicFunctions.ParkingRecordService;
import utils.model.Resp;

/**
 * 视频车进车出记录
 * @author jinx
 *
 */
@Controller
public class ParkingRecodeController extends BaseController{

	private Logger LOG = LoggerFactory.getLogger(ParkingRecodeController.class);
	
	@Autowired
	private ParkingRecordService parkingRecordService;
	
	/**
	 * 车辆进出车记录列表页面
	 * @return
	 */
	@RequestMapping(value = "/p/recode/list")
	public String list(){
		return "/page/recode_list";
	}
	
	/**
	 * 进出车记录列表
	 * @param p
	 * @param parkNumber
	 * @return
	 */
	@RequestMapping(value = "/d/recode/list")
	@ResponseBody
	public Resp<?> pageList(Integer p,String parkNumber){
		Resp<?> resp = new Resp<>(false);
		try {
			return  new Resp<>(parkingRecordService.findByPage(p, parkNumber));
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 删除车进车出记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/d/recode/del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingRecordService.delete(id);
			return  new Resp<>(true);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
}
