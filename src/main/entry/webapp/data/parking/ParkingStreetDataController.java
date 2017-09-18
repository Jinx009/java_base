package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingStreet;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingStreetService;
import utils.Resp;

/**
 * 街道管理
 * @author jinx
 *
 */
@Controller
@RequestMapping(value = "/home/d")
public class ParkingStreetDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingStreetDataController.class);

	@Autowired
	private ParkingStreetService parkingStreetService;
	
	/**
	 * 根据区域获取街道信息
	 * @param areaId
	 * @return
	 */
	@RequestMapping(path = "/streetList")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingStreet> list =  parkingStreetService.findAll();
			resp = new Resp<>(list);
			logger.warn("data:{}",resp);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
	
	/**
	 * 删除街道信息
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/deleteStreet")
	@ResponseBody
	public Resp<?> delete(@RequestParam(name = "id",required = false)Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingStreetService.delete(id);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
	
	/**
	 * 新建街道
	 * @param name
	 * @param desc
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/saveStreet")
	@ResponseBody
	public Resp<?> save(@RequestParam(name = "name",required = false)String name,
						@RequestParam(name = "desc",required = false)String desc,
						@RequestParam(name = "areaId",required = false)Integer areaId){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingStreetService.save(areaId,name,desc);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
	
	
	
	
}
