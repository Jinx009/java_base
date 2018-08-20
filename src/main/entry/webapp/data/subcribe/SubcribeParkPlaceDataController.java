package main.entry.webapp.data.subcribe;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.subcribe.SubcribeParkPlaceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/subcribe/d/place")
public class SubcribeParkPlaceDataController extends BaseController{

//	private static final Logger log = LoggerFactory.getLogger(SubcribeParkPlaceDataController.class);
	
	@Autowired
	private SubcribeParkPlaceService subcribeParkPlaceService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		return new Resp<>(subcribeParkPlaceService.list());
	}
	
}
