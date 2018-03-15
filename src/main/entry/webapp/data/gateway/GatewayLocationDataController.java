package main.entry.webapp.data.gateway;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.StringUtil;
import database.models.project.ProGatewayLocation;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewayLocationService;
import utils.BaseConstant;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/rest/location")
public class GatewayLocationDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayLocationDataController.class);
	
	@Autowired
	private ProGatewayLocationService proGatewayLocationService;
	
	@RequestMapping(path = "/all")
	@ResponseBody
	public Resp<?> getByAppId(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String appId = getString(data, BaseConstant.APP_ID);
			if(StringUtil.isBlank(token)||StringUtil.isBlank(appId)){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			if(!checkToken(token,getToken(appId))){
				resp.setMsg(RespData.TOKEN_CHECK_ERROR);
				return resp;
			}
			List<ProGatewayLocation> list = proGatewayLocationService.getByAppId(appId);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
