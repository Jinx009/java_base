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
@Table(name = "tbl_gnss_device")
public class GnssDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "mac")
	private String mac;
	@Column(name = "fix_type")
	private Integer fixType;
	@Column(name = "fix_status")
	private String fixStatus;
	@Column(name = "lng")
	private String lng;
	@Column(name = "lat")
	private String lat;
	@Column(name = "height")
	private String height;
	@Column(name = "hmsl")
	private String hmsl;
	@Column(name = "hor_acc")
	private String horAcc;
	@Column(name = "ver_acc")
	private String verAcc;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	@Column(name = "num")
	private Integer num;
	

}
