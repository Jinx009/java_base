package utils.atmosphere;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataModel {

//	{
//		"province": "安徽省",
//		"co": 0.8,
//		"o3_24h": "",
//		"pollutions": "颗粒物(PM10)",
//		"quality": "良",
//		"pm10_24h": "",
//		"no2": 20,
//		"city": "马鞍山市",
//		"o3_8h": "",
//		"so2_24h": "",
//		"pm2_5_24h": "",
//		"latitude": 31.69678,
//		"o3": 127,
//		"city_code": 340500,
//		"pm2_5": 34,
//		"co_24h": "",
//		"so2": 16,
//		"aqi": 57,
//		"pm10": 64,
//		"level": 2,
//		"longitude": 118.52462,
//		"o3_8h_24h": "",
//		"no2_24h": "",
//		"pubtime": "2018-07-24 10:00:00"
//	}
	private String province;
	private String co;
	private String pollutions;
	private String quality;
	private String pm10_24h;
	private String no2;
	private String city;
	private String o3_8h;
	private String so2_24h;
	private String pm2_5_24h;
	private String latitude;
	private String o3;
	private String city_code;
	private String pm2_5;
	private String co_24h;
	private String so2;
	private String aqi;
	private String pm10;
	private String level;
	private String longitude;
	private String o3_8h_24h;
	private String pubtime;
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
