package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.BaseDao;
import database.models.project.ProPhoto;
import service.basicFunctions.service.ProPhotoService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/photo")
public class ProPhotoController extends BaseDao<ProPhoto> {

	private static Logger log = LoggerFactory.getLogger(ProPhotoController.class);
	
	@Autowired
	private ProPhotoService proPhotoService;
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String dateStr,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			proPhotoService.save(dateStr, type);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proPhotoService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	public Resp<?> findByDateStr(String dateStr,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			ProPhoto proPhoto = proPhotoService.findByDateStr(dateStr, type);
			if(proPhoto!=null){
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proPhotoService.pageList(p));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
