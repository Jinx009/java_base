package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProGatewayAccessControlLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewayAccessControlLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/rest/accessControl")
public class GatewayAccessControlDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayAccessControlDataController.class);
	
	@Autowired
	private ProGatewayAccessControlLogService proGatewayAccessControlLogService;
	
	@RequestMapping(path = "/PushOpenDoorMsg")
	@ResponseBody
	public String post(ProGatewayAccessControlLog proAccessControlLog){
		try {
			proGatewayAccessControlLogService.save(proAccessControlLog);
			return "200";
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return "400";
	}
	
	@RequestMapping(path = "/log")
	@ResponseBody
	public Resp<?> get(Integer p ){
		try {
			return new Resp<>(proGatewayAccessControlLogService.pageList(p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
}
