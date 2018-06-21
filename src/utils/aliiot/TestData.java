package utils.aliiot;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.fastjson.JSON;
public class TestData {

	public static void main(String[] args) throws UnsupportedEncodingException {
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
				//请求参数：domain、path,固定值
				ApiResponse response = syncClient.postBody("official.api.feifengiot.com","/iotx/developer/execute", request);
	}
	
	
}
