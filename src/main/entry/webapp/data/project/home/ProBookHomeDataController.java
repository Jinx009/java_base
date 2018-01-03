package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProBook;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProBookService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_book")
public class ProBookHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProBookHomeDataController.class);
	
	@Autowired
	private ProBookService proBookService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProBook> list = proBookService.homeList(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
