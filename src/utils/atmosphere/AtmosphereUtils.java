package utils.atmosphere;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AtmosphereUtils {

	public static void main(String[] args) {
        System.out.println(get("马鞍山市"));
    }
	
	public static DataModel get(String cityName){
		String url = "https://service-5v2rb5oj-1255304211.ap-shanghai.apigateway.myqcloud.com/release" +
				"/api/v1/nation_air_by_city/city_realtime_list?city="+cityName;
		String secretId = "AKIDMwQttRjRZK4iuqTz4q67CqMn7vfNJynqw9p2";
		String secretKey = "d6P3428Ql15cu1RFLp07M33BoxJ111CyJvRzcFQ";
		String result = SignAndSend.sendGet(url, secretId, secretKey);
		List<DataModel> dataModels = JSONArray.parseArray(JSONObject.parseObject(result).getString("data"), DataModel.class);
		if(dataModels!=null&&!dataModels.isEmpty()){
			return dataModels.get(0);
		}
		return null;
	}
	
}
