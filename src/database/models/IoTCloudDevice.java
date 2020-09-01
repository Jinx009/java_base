package database.models;

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
@Table(name="pro_device")
public class IoTCloudDevice {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mac")
	private String mac;
	@Column(name = "imei")
	private String imei;
	@Column(name = "local_ip")
	private String localIp;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "type")
	private Integer type; //1电信 2 联通
	@Column(name = "device_id")
	private String deviceId;
	@Column(name = "udp_ip")
	private String udpIp;
	@Column(name = "udp_port")
	private Integer udpPort;
	@Column(name = "sim_card")
	private String simCard;
	@Column(name = "park_name")
	private String parkName;
	@Column(name = "park_number")
	private String parkNumber;
	@Column(name = "area")
	private String area;
	@Column(name = "location")
	private String location;
	@Column(name = "data_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTime;
	@Column(name = "send_a")
	private String sendA;
	@Column(name = "send_b")
	private String sendB;
	@Column(name = "send_c")
	private String sendC;
	@Column(name = "send_d")
	private String sendD;
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	@Column(name = "data_num")
	private Integer dataNum;
	@Column(name = "max_x")
	private Double maxX;
	@Column(name = "max_y")
	private Double maxY;
	@Column(name = "send_base")
	private String sendBase;
	@Column(name = "lng")
	private Double lng;
	@Column(name = "lat")
	private Double lat;
	@Column(name ="is_correct")
	private Integer isCorrect;
	@Column(name ="sensor_send_status")
	private Integer sensorSendStatus;
	@Column(name = "secret")
	private String secret;
	@Column(name = "work_status")
	private Integer workStatus;
}
