package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.fastjson.JSON;

import main.entry.webapp.BaseController;
import utils.Resp;
import utils.aliiot.IoTApiRequest;
import utils.aliiot.SyncApiClient;

@Controller
@RequestMapping(value = "/aliiot")
public class AliIoTController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(AliIoTController.class);
	
	@RequestMapping(path = "/send")
	@ResponseBody
	public Resp<?> send(){
		try {
			SyncApiClient syncClient = SyncApiClient.newBuilder()
					.appKey("24936486")
					.appSecret("b6a540e792b6cc7feeff098ba3e41162")
					.build();
					IoTApiRequest request = new IoTApiRequest();
					Map<String,Object> data = new HashMap<String, Object>();
					data.put("ItemId", request.getId());
					data.put("GmtTime", new Date().getTime());
					data.put("voltage", 3.61);
					data.put("signal", "-33");
					data.put("mac", "0001171026000004");
					//设api的版本,固定值
					request.setApiVer("1.0.0");
					request.putParam("schemaId", "BF7886B50BBA95D6");
					request.putParam("reqContent", JSON.toJSONString(data));
					log.warn("data:{}",JSON.toJSONString(request));
					//请求参数：domain、path,固定值
					ApiResponse response = syncClient.postBody("official.api.feifengiot.com","/iotx/developer/execute", request);
					log.warn("response:{}",JSON.toJSONString(response));
		} catch (Exception e) {
			log.error("erro:{}",e);
		}
		return new Resp<>(true);
	}
	
}
