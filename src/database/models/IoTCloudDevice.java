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
}
