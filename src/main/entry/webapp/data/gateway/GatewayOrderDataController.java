package main.entry.webapp.data.gateway;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.StringUtil;
import main.entry.webapp.BaseController;
import utils.BaseConstant;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/rest/order")
public class GatewayOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayOrderDataController.class);
	
	@RequestMapping(path = "/getByParkNo")
	@ResponseBody
	public Resp<?> getLastOrder(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String parkNo = getString(data, BaseConstant.PARK_NO);
			Integer areaId = getInt(data, BaseConstant.AREA_ID_NAME);
			log.warn("token:{},parkNo:{},areaId:{}",token,parkNo,areaId);
			if(StringUtil.isBlank(token)||StringUtil.isBlank(parkNo)||areaId==null){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			if(!checkToken(token)){
				resp.setMsg(RespData.TOKEN_CHECK_ERROR);
				return resp;
			}
			
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return resp;
	}
	
}
