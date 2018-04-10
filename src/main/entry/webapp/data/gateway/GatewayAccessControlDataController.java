package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProAccessControlLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProAccessControlLogService;

@Controller
@RequestMapping(value = "/rest/accessControl")
public class GatewayAccessControlDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayAccessControlDataController.class);
	
	@Autowired
	private ProAccessControlLogService proAccessControlLogService;
	
	@RequestMapping(path = "")
	@ResponseBody
	public String post(ProAccessControlLog proAccessControlLog){
		try {
			proAccessControlLogService.save(proAccessControlLog);
			return "200";
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return "400";
	}
}
