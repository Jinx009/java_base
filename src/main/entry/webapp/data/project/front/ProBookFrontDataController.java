package main.entry.webapp.data.project.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProBook;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProBookService;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d/pro_book")
public class ProBookFrontDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProBookFrontDataController.class);
	
	@Autowired
	private ProBookService proBookService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProBook> list = proBookService.list();
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
