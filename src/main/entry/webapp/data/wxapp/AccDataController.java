package main.entry.webapp.data.wxapp;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.wxapp.WxAccModel;
import main.entry.webapp.BaseController;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/acc")
public class AccDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(AccDataController.class);

	@RequestMapping(path = "/lost")
	@ResponseBody
	public Resp<?> getLost() {
		Resp<?> resp = new Resp<>(false);
		try {
			String jsonStr = HttpUtils.get("http://106.14.94.245:8091/weapp/d/lost");
			JSONObject obj = JSONObject.parseObject(jsonStr);
			List<WxAccModel> list = JSONObject.parseArray(obj.getString("data"), WxAccModel.class);
			for (WxAccModel model : list) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUdpIp(sdf.format(model.getUpdateTime()));
			}
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> getList(String secret) {
		Resp<?> resp = new Resp<>(false);
		try {
			String jsonStr = HttpUtils.get("http://106.14.94.245:8091/weapp/d/devices");
			JSONObject obj = JSONObject.parseObject(jsonStr);
			List<WxAccModel> list = JSONObject.parseArray(obj.getString("data"), WxAccModel.class);
			for (WxAccModel model : list) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUdpIp(sdf.format(model.getUpdateTime()));
			}
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "new")
	@ResponseBody
	public Resp<?> getList(String mac,double lng,double lat,String parkName,String secret) {
		Resp<?> resp = new Resp<>(false);
		try {
			String jsonStr = HttpUtils.get("http://106.14.94.245:8091/weapp/d/new?mac="+mac+"&lng="+lng+"&lat="+lat+"&parkName="+parkName+"&secret="+secret);
			return new Resp<>(JSONObject.parseObject(jsonStr));
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

}
