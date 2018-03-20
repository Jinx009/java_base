package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProUserService;

@RequestMapping(value = "/data/commom")
@Controller
public class CommonDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(CommonDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	
	
}
