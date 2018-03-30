package database.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Date createTime;
	@Column(name = "type")
	private Integer type; //1电信 2 联通
	
}
