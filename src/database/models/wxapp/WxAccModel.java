package database.models.wxapp;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxAccModel {

//	{
//	    "id": 1534,
//	    "mac": "0009190906000044",
//	    "imei": "868681044721098",
//	    "localIp": "QJ_ZHANWAY_V_3.0_WUHAN",
//	    "createTime": 1568268542000,
//	    "type": 2,
//	    "deviceId": "06a64e29-c198-418a-9082-3ff5ab8b55f0",
//	    "udpIp": "119.97.193.69",
//	    "udpPort": 99,
//	    "simCard": "8986111828200987748BQ_01010400146",
//	    "parkName": "蓑衣槽",
//	    "parkNumber": null,
//	    "area": null,
//	    "location": null,
//	    "dataTime": 1588038952000,
//	    "sendA": null,
//	    "sendB": null,
//	    "sendC": null
//	}
	private Integer id;
	private String mac;
	private String imei;
	private String localIp;
	private Date createTime;
	private String deviceId;
	private Integer type;
	private String udpIp;
	private String simCard;
	private Integer udpPort;
	private String parkName;
	private String parkNumber;
	private String area;
	private String location;
	private Date dataTime;
	private Date updateTime;
	private String sendA;
	private String sendB;
	private String sendC;
	private String sendD;
	
	
	
}
