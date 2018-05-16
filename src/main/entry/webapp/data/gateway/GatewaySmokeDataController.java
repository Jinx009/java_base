package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProGatewaySmokeData;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewaySmokeDataService;
import utils.Resp;

@RequestMapping(value = "/rest/smoke")
@Controller
public class GatewaySmokeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewaySmokeDataController.class);
	
	@Autowired
	private ProGatewaySmokeDataService proGatewaySmokeDataService;
	
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProGatewaySmokeData> list = proGatewaySmokeDataService.pageList(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
