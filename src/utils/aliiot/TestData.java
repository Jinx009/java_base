package utils.aliiot;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class TestData {

	public static void main(String[] args) throws UnsupportedEncodingException {
		 SyncApiClient syncClient = SyncApiClient.newBuilder()
			        .appKey("24936486")
			        .appSecret("b6a540e792b6cc7feeff098ba3e41162")
			        .build();

			    IoTApiRequest request = new IoTApiRequest();
			    //设置api的版本
			    request.setApiVer("1.1.0");
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("ItemId", request.getId().replaceAll("\\-", ""));
				data.put("GmtTime", new Date().getTime());
				data.put("voltage", 3.61);
				data.put("signal", "-33");
				data.put("mac", "0001171026000002");
			    // 接口参数
				request.putParam("deviceName","0001171026000002");
			    request.putParam("schemaId","BF7886B50BBA95D6");
			    request.putParam("reqContent",JSONObject.toJSONString(data));

			    //请求参数域名、path、request
			    System.out.println(JSON.toJSONString(request));
			    ApiResponse response = syncClient.postBody("api.link.aliyun.com",
			        "/data/dataGroup/upload", request, true);
			    System.out.println(
			        "response code = " + response.getStatusCode() + " response content = " + new String(response.getBody(),
			            "utf-8"));
	}
	
	
}
