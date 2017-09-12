package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingArea;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingAreaService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingAreaDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingAreaDataController.class);
	
	@Autowired
	private ParkingAreaService parkingAreaService;
	
	/**
	 * 返回所以地区列表
	 * @return
	 */
	@RequestMapping(path = "/areaList")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingArea> list = parkingAreaService.findAll();
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,list);
			logger.warn("data:{}",resp);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
	
	/**
	 * 新建区域
	 * @param name
	 * @param desc
	 * @return
	 */
	@RequestMapping(path = "/saveArea")
	@ResponseBody
	public Resp<?> save(@RequestParam(name = "name",required = false)String name,
						@RequestParam(name = "desc",required = false)String desc){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingAreaService.save(name,desc);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,"");
			logger.warn("data:{}",resp);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
	
	/**
	 * 删除(隐藏)区域
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/delArea")
	@ResponseBody
	public Resp<?> delete(@RequestParam(name = "id",required = false)Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingAreaService.delete(id);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,"");
			logger.warn("data:{}",resp);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
	
	/**
	 * 更新
	 * @param id
	 * @param name
	 * @param desc
	 * @return
	 */
	@RequestMapping(path = "/updateArea")
	@ResponseBody
	public Resp<?> update(@RequestParam(name = "id",required = false)Integer id,
						  @RequestParam(name = "name",required = false)String name,
						  @RequestParam(name = "desc",required = false)String desc){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingAreaService.update(id,name,desc);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,"");
			logger.warn("data:{}",resp);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
			return resp;
		}
	}
			
	
}
