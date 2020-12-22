package database.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_gnss_rtk_device")
public class GnssRtkDevice {

//	{
//		"_id": {
//			"$oid": "5ed56b58a582b90e7dba9113"
//		},
//		"lat": 31.245733928880256,
//		"height": 62.92901985959338,
//		"fixrate": 0.31924958424451383,
//		"satellites": 9,
//		"rovertag": "043007410002",
//		"datetime": "2020-06-01T20:00:00.000Z",
//		"east": -1.747852796581769,
//		"north": 0.8379785734022667,
//		"quality": 1,
//		"updatetime": "2020-06-01T20:55:52.626Z",
//		"window": 24,
//		"basetag": "043007410001",
//		"coverrate": 9.529161146775687,
//		"lng": 121.61762743665412,
//		"up": 0.293319566257618
//	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "mongo_id")
	private String _id;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "height")
	private Double height;
	@Column(name = "fixrate")
	private Double fixrate;
	@Column(name = "satellites")
	private Integer satellites;
	@Column(name = "rovertag")
	private String rovertag;
	@Column(name = "datetime")
	private String datetime;
	@Column(name = "east")
	private Double east;
	@Column(name = "north")
	private Double north;
	@Column(name = "quality")
	private Integer quality;
	@Column(name = "updatetime")
	private String updatetime;
	@Column(name = "window")
	private Integer window;
	@Column(name = "basetag")
	private String basetag;
	@Column(name = "coverrate")
	private Double coverrate;
	@Column(name = "lng")
	private Double lng;
	@Column(name = "up")
	private Double up;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "mac")
	private String mac;
	@Column(name = "imei")
	private String imei;
	@Column(name = "data_type")//报文类型
	private String dataType;
	@Column(name = "switch_type")//是都开启解算
	private Integer switchType;
	@Column(name = "update_time2")
	private String updatetime2;
	@Column(name = "location")
	private String location;
	@Column(name = "device_id")
	private String deviceId;
	@Column(name = "apikey")
	private String apikey;
	
}
