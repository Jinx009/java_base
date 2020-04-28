package main.entry.webapp.data.wxapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.wxapp.WxAccModel;
import main.entry.webapp.BaseController;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/acc")
public class AccDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(AccDataController.class);

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> getBroken() {
		Resp<?> resp = new Resp<>(false);
		try {
			String jsonStr = HttpUtils.get("http://106.14.94.245:8091/iot/device/wuhan");
			JSONObject obj = JSONObject.parseObject(jsonStr);
			List<WxAccModel> list = JSONObject.parseArray(obj.getString("data"), WxAccModel.class);
			List<WxAccModel> list2 = new ArrayList<WxAccModel>();
			Map<String, Date> map = new HashMap<String, Date>();
			if (list != null && !list.isEmpty()) {
				for (WxAccModel model : list) {
					map.put(model.getMac(), model.getDataTime());
				}
				for (WxAccModel model : list) {
					if (StringUtil.isNotBlank(model.getSendA())) {
						map.put(model.getSendA(), model.getDataTime());
					}
					if (StringUtil.isNotBlank(model.getSendB())) {
						map.put(model.getSendB(), model.getDataTime());
					}
					if (StringUtil.isNotBlank(model.getSendC())) {
						map.put(model.getSendC(), model.getDataTime());
					}
				}
				for (WxAccModel model : list) {
					model.setDataTime(map.get(model.getMac()));
					Date date = new Date();
					if (model.getDataTime() != null) {
						if ((date.getTime() - model.getDataTime().getTime()) / (3600 * 1000) > 24) {
							WxAccModel wx = new WxAccModel();
							wx.setMac(model.getMac());
							wx.setParkName(model.getParkName());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							wx.setUdpIp(sdf.format(model.getDataTime()));
							list2.add(wx);
						}
					}
				}
				return new Resp<>(list2);
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

	public static void main(String[] args) {
		String jsonStr = HttpUtils.get("http://106.14.94.245:8091/iot/device/wuhan");
		JSONObject obj = JSONObject.parseObject(jsonStr);
		List<WxAccModel> list = JSONObject.parseArray(obj.getString("data"), WxAccModel.class);
		List<WxAccModel> list2 = new ArrayList<WxAccModel>();
		Map<String, Date> map = new HashMap<String, Date>();
		if (list != null && !list.isEmpty()) {
			for (WxAccModel model : list) {
				map.put(model.getMac(), model.getDataTime());
			}
			for (WxAccModel model : list) {
				if (StringUtil.isNotBlank(model.getSendA())) {
					map.put(model.getSendA(), model.getDataTime());
				}
				if (StringUtil.isNotBlank(model.getSendB())) {
					map.put(model.getSendB(), model.getDataTime());
				}
				if (StringUtil.isNotBlank(model.getSendC())) {
					map.put(model.getSendC(), model.getDataTime());
				}
			}
			for (WxAccModel model : list) {
				model.setDataTime(map.get(model.getMac()));
				Date date = new Date();
				if (model.getDataTime() != null) {
					if ((date.getTime() - model.getDataTime().getTime()) / (3600 * 1000) > 24) {
						WxAccModel wx = new WxAccModel();
						wx.setMac(model.getMac());
						wx.setParkName(model.getParkName());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						wx.setUdpIp(sdf.format(model.getDataTime()));
						list2.add(wx);
					}
				}
			}
		}
		System.out.println(JSONObject.toJSONString(list2));
	}

}
