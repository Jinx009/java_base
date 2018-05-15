package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.project.ProGatewayBed;
import database.models.project.model.ProGatewayBedModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewayBedService;
import utils.Resp;

@Controller
@RequestMapping(value = "/rest/bed")
public class GatewayBedDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(GatewayBedDataController.class);

	@Autowired
	private ProGatewayBedService proGatewayBedService;
	
	@RequestMapping(path = "/push")
	@ResponseBody
	public Resp<?> push(@RequestBody String s){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("data:{}",s);
			ProGatewayBedModel proGatewayBedModel = JSONObject.parseObject(s,ProGatewayBedModel.class);
			ProGatewayBed proGatewayBed = proGatewayBedModel.getData();
			proGatewayBedService.save(proGatewayBed);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
