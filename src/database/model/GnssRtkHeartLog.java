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
@Table(name = "tbl_gnss_rtk_heartlog")
public class GnssRtkHeartLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "mac_model")
	private String macModel;
	@Column(name = "hard_edition")
	private String hardEdition;
	@Column(name = "soft_edition")
	private String softEdition;
	@Column(name = "mac")
	private String mac;
	@Column(name = "plate_type")
	private String plateType;
	@Column(name = "voltage")
	private String voltage;
	@Column(name = "tem")
	private String tem;
	@Column(name = "lng")
	private String lng;
	@Column(name = "lat")
	private String lat;
	@Column(name = "height")
	private String height;
	@Column(name = "rssi")
	private String rssi;
	@Column(name = "ber")
	private String ber;
	@Column(name = "base_data")
	private String baseData;
	
}
