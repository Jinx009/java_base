package main.entry.webapp.data.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import database.models.job.CommonJob;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import service.basicFunctions.job.CommonJobService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/common/job")
public class CommonJobDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonJobDataController.class);

	@Autowired
	private HttpService httpService;
	@Autowired
	private CommonJobService commonJobService;
	
	@RequestMapping(path = "/save/cfgsensor")
	@ResponseBody
	public Resp<?> saveCfgsensorC(String mac,String target,String serviceName){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("mac", target);
			map.put("cmd", "cfgsensor");
			Map<String, String> data = new HashMap<String,String>();
			data.put("order", "R");
			data.put("data", "0");
			data.put("mac", mac);
			map.put("obj", data);
			String result = httpService.postJson(HttpData.jobAdd(), JSON.toJSONString(map));
			JSONObject jsonObject = JSONObject.parseObject(result);
			String code = jsonObject.getString("respCode");
			logger.warn("data:{}",result);;
			if(BaseConstant.HTTP_OK_CODE.equals(code)){
				String jsonStr =  jsonObject.getString(BaseConstant.PARAMS);
				JSONObject jsObject = JSONObject.parseObject(jsonStr);
				Integer id = jsObject.getInteger("id");
				CommonJob commonJob = new CommonJob();
				commonJob.setBaseId(id);
				commonJob.setCmd("cfgsensor");
				commonJob.setJobDetail(JSON.toJSONString(data));
				commonJob.setServiceName(serviceName);
				commonJob.setStatus(0);
				commonJob.setTarget(target);
				commonJobService.save(commonJob);
				resp = new Resp<>(true);
			}else{
				String msg = jsonObject.getString("msg");
				resp.setMsg(msg);
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/find")
	@ResponseBody
	public Resp<?> find(String serviceName){
		Resp<?> resp = new Resp<>(false);
		try {
			List<CommonJob> list = commonJobService.findNotFinish(serviceName);
			if(list!=null&&!list.isEmpty()){
				resp = new Resp<>(true);
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/all")
	@ResponseBody
	public Resp<?> all(String serviceName){
		Resp<?> resp = new Resp<>(false);
		try {
			List<CommonJob> list = commonJobService.findAll(serviceName);
			if(list!=null&&!list.isEmpty()){
				resp = new Resp<>(list);
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	
}
