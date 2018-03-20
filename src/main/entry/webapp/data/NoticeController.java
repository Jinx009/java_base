package main.entry.webapp.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import utils.Base64Utils;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

	@SuppressWarnings("rawtypes")
	@RequestMapping(path = "/data")
	@ResponseBody
	public String getNotice(HttpServletRequest request) {
		Resp<?> resp = new Resp<>(true);
		Map<?, ?> map = request.getParameterMap();
		Set<?> keSet = map.entrySet();
		for (Iterator<?> itr = keSet.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			Object ok = me.getKey();
			Object ov = me.getValue();
			String[] value = new String[1];
			if (ov instanceof String[]) {
				value = (String[]) ov;
			} else {
				value[0] = ov.toString();
			}

			for (int k = 0; k < value.length; k++) {
				log.warn(ok + "=" + value[k]);
			}
		}
		return "Success";
	}

	public static void main(String[] args) {
		// {
		// "headers": {"authorization":"Basic dWF0YWRlcDpBc2RmMSM="},
		// "url": "http://<callback_url>/m2m/impact/callback"
		// }' 'http://<impact_url>/m2m/applications/registration'

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("authorization", "Basic " + Base64Utils.getEncodedBase64("zhanway:Zhanway!23"));
		map.put("headers", data);
		map.put("url", "http://106.14.94.245:8091/notice/data");
		HttpUtils.putJson("http://223.167.110.4:8000/m2m/applications/registration", JSONObject.toJSONString(map));

	}

}
