package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewaySmokeDataService;

@RequestMapping(value = "/rest/smoke")
@Controller
public class GatewaySmokeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewaySmokeDataController.class);
	
	@Autowired
	private ProGatewaySmokeDataService proGatewaySmokeDataService;
}
